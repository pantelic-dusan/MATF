#include "mainwindow.h"
#include "ui_mainwindow.h"

#include <QDebug>
#include <QMessageBox>

QVector<QVector<double>> MainWindow::matrix;
QMutex MainWindow::mutex_matrix;
int MainWindow::size = 0;

MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent)
    , ui(new Ui::MainWindow)
    , timer(this)
{
    ui->setupUi(this);

    QObject::connect(ui->pushButtonStartMeasure, SIGNAL(clicked()),
                     this, SLOT(onPushButtonStartMeasure()));

}

MainWindow::~MainWindow()
{
    ui->widgetShowResult->close();
    delete ui;

    for (auto thread: threads)
    {
        if (!thread->isFinished())
        {
            thread->quit();
        }
        delete thread;
    }
}

void MainWindow::onPushButtonStartMeasure()
{
    const auto numberStr = ui->lineEditSize->text();
    bool parsed;
    const auto number = numberStr.toInt(&parsed);

    if (!parsed || number <= 0)
    {
        QMessageBox msgBox;
        msgBox.setText("Unesite ispravan pozitivan broj");
        msgBox.exec();
        return;
    }

    ui->groupBox->setDisabled(true);

    MainWindow::mutex_matrix.lock();
    MainWindow::size = number;

    for (auto i=0; i<size; i++) {
        QVector<double> row;
        for (auto j=0; j<size; j++) {
            row.push_back((qrand()%15+15));
        }
        matrix.push_back(row);
    }

    for (auto i=0; i<size; i++) {
        for (auto j=0; j<size; j++) {
            auto thread = new CellThread(i, j);
            QObject::connect(thread, &CellThread::threadFinished,
                             this, &MainWindow::onThreadFinished,
                             Qt::QueuedConnection);
            threads.push_back(thread);
        }
    }

    QObject::connect(&timer, &QTimer::timeout,
                     this, &MainWindow::onTimerTimeout);
    timer.start(1500);

    mutex_matrix.unlock();

}

void MainWindow::onThreadFinished(int i, int j, double new_value)
{
    MainWindow::mutex_matrix.lock();

    matrix[i][j] = new_value;

    QString matrix_html("<table style=\"border-collapse: collapse;\">");
    for (const auto & row: matrix) {
        matrix_html += "<tr>";
        for (const auto &cell: row) {
            matrix_html +=
                                "<td style=\"border: 1px solid black; padding: 10px;\">"
                                    + QString::number(cell) +
                                "</td>";
        }
        matrix_html += "</tr>";
    }
    matrix_html += "</table>";
    ui->widgetShowResult->setHtml(matrix_html);
    mutex_matrix.unlock();
    const auto thred_id = i*MainWindow::size + j;
    MainWindow::threads[thred_id]->quit();
}

void MainWindow::onTimerTimeout()
{
    for (auto thread_iter = threads.begin(); thread_iter != threads.end(); thread_iter++) {
        if (!(*thread_iter)->isFinished()) {
            (*thread_iter)->quit();
        }

        (*thread_iter)->start();
    }
}
