#ifndef WIDGET_H
#define WIDGET_H

#include <QWidget>
#include <QPushButton>
#include <QLabel>

class Widget : public QWidget
{
    Q_OBJECT

public:
    Widget(QWidget *parent = nullptr);
    ~Widget();

public Q_SLOTS:
    void onButtonStart();
    void onPiCalcThreadFinished(double value);

private:
    QPushButton *buttonStart;
    QLabel *labelValue;
};
#endif // WIDGET_H
