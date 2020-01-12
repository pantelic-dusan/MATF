#include "cellthread.h"
#include "mainwindow.h"

CellThread::CellThread(const int i, const int j)
{
    this->i = i;
    this->j = j;
}

void CellThread::run()
{
    MainWindow::mutex_matrix.lock();

    auto old_value = MainWindow::matrix[i][j];
    auto addition = 0.0;
    auto n = MainWindow::matrix.size();
    const auto coefficient = 0.1;

    if (i > 0)
    {
        addition += coefficient*(MainWindow::matrix[i-1][j] - old_value);
    }

    if (j > 0)
    {
        addition += coefficient*(MainWindow::matrix[i][j-1] - old_value);
    }

    if (i < n-1)
    {
        addition += coefficient*(MainWindow::matrix[i+1][j] - old_value);
    }

    if (j < n-1)
    {
        addition += coefficient*(MainWindow::matrix[i][j+1] - old_value);
    }
    MainWindow::mutex_matrix.unlock();

    emit threadFinished(i,j, old_value + addition);
}
