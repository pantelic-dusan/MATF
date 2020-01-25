#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include <QLabel>

QT_BEGIN_NAMESPACE
namespace Ui { class MainWindow; }
QT_END_NAMESPACE

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    MainWindow(QWidget *parent = nullptr);
    ~MainWindow();

public Q_SLOTS:
    void onButtonGenerate();
    void onButtonExit();
    void onLetterGenerated(int i, char c);
    void onGenerationComplete();

private:
    Ui::MainWindow *ui;
    QVector<QLabel*> letters;
    int num_working_threads = 0;
};
#endif // MAINWINDOW_H
