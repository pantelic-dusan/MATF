#include "mainwindow.h"

#include <QVBoxLayout>
#include <QTime>
#include <QLineEdit>

MainWindow::MainWindow(QWidget *parent)
    : QWidget(parent)
{
    buttonStart = new QPushButton("Izracunaj", this);
    labelValue = new QLabel("...", this);

    auto layout = new QVBoxLayout();

    QWidget::setLayout(layout);
    QWidget::resize(300,100);

    layout->addWidget(buttonStart);
    layout->addWidget(labelValue);

    layout->addWidget(new QLineEdit(this));

    QObject::connect(buttonStart, &QPushButton::clicked, this, &MainWindow::onButtonStart);
}

MainWindow::~MainWindow()
{
}

double MainWindow::calculatePiValue() const {

    qsrand(static_cast<unsigned>(QTime(0,0,0).secsTo(QTime::currentTime())));
    auto hits = 0.0, count = 0.0;

    while (hits < 1000000) {
        const double x = (qrand()%512)/512.0;
        const double y = (qrand()%512)/512.0;

        ++count;
        if (x*x + y*y <= 1)
            ++hits;
    }

    return 4*hits/count;

}

void MainWindow::onButtonStart() {
    labelValue->setText("Calculating ...");
    labelValue->setText(QString::number(calculatePiValue()));
}


