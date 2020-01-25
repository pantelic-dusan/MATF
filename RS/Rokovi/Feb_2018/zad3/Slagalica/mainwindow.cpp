#include "mainwindow.h"
#include "ui_mainwindow.h"

#include "generator.h"

MainWindow::MainWindow(QWidget *parent)
    : QMainWindow(parent)
    , ui(new Ui::MainWindow)
{
    ui->setupUi(this);

    this->letters.push_back(ui->labelLetter1);
    this->letters.push_back(ui->labelLetter2);
    this->letters.push_back(ui->labelLetter3);
    this->letters.push_back(ui->labelLetter4);

    connect(ui->pushButtonGenerate, SIGNAL(clicked()), this, SLOT(onButtonGenerate()));

    connect(ui->pushButtonExit, &QPushButton::clicked, this, &MainWindow::onButtonExit);
}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::onButtonGenerate()
{
    ui->pushButtonGenerate->setDisabled(true);
    this->num_working_threads = 4;

    QVector<Generator *> threads(this->num_working_threads);
    for (int i = 0; i < this->num_working_threads; ++i) {
       Generator *thread = new Generator(i);
       threads[i] = thread;
       connect(thread, &Generator::letterGenerated, this, &MainWindow::onLetterGenerated, Qt::QueuedConnection);
       connect(thread, &Generator::generationComplete, this, &MainWindow::onGenerationComplete,Qt::QueuedConnection);
       connect(thread, &Generator::generationComplete, this, &MainWindow::deleteLater, Qt::QueuedConnection);
       thread->start();
    }


}

void MainWindow::onButtonExit()
{
    exit(0);
}

void MainWindow::onLetterGenerated(int i, char c)
{
    this->letters[i]->setText(QString(c));
}

void MainWindow::onGenerationComplete()
{
    this->num_working_threads--;
    if (this->num_working_threads == 0) {
        ui->pushButtonGenerate->setDisabled(false);
    }
}
