#include "widget.h"

#include <QVBoxLayout>
#include <QLineEdit>

#include "picalcthread.h"

Widget::Widget(QWidget *parent)
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

    QWidget::connect(buttonStart, &QPushButton::clicked, this, &Widget::onButtonStart);

}

Widget::~Widget()
{
}

void Widget::onButtonStart() {
    labelValue->setText("Calculating...");

    auto thread = new PiCalcThread();

    QObject::connect(thread, &PiCalcThread::calculatedPi,
                     this, &Widget::onPiCalcThreadFinished,
                     Qt::QueuedConnection);

    QObject::connect(thread, &PiCalcThread::calculatedPi,
                     thread, &QObject::deleteLater,
                     Qt::QueuedConnection);

    thread->start();

}

void Widget::onPiCalcThreadFinished(double value) {
    labelValue->setText(QString::number(value));
}
