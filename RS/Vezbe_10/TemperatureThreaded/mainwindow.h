#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include <QWidget>
#include <QVector>
#include <QMutex>
#include <QTimer>

#include "cellthread.h"

QT_BEGIN_NAMESPACE
namespace Ui { class MainWindow; }
QT_END_NAMESPACE

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    MainWindow(QWidget *parent = nullptr);
    ~MainWindow();

    static QVector<QVector<double>> matrix;
    static QMutex mutex_matrix;
    static int size;

public slots:
    void onPushButtonStartMeasure();
    void onThreadFinished(int i, int j, double new_value);

private slots:
    void onTimerTimeout();

private:
    Ui::MainWindow *ui;
    QVector<CellThread*> threads;
    QTimer timer;
};
#endif // MAINWINDOW_H
