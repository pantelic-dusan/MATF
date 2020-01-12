#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include <QPushButton>
#include <QLabel>

class MainWindow : public QWidget
{
    Q_OBJECT

public:
    MainWindow(QWidget *parent = nullptr);
    ~MainWindow();

public Q_SLOTS:
    void onButtonStart();

private:
    double calculatePiValue() const;
    QPushButton *buttonStart;
    QLabel *labelValue;
};
#endif // MAINWINDOW_H
