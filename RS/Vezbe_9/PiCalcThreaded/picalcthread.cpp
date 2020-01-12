#include "picalcthread.h"

#include <QTime>
#include <QDebug>

PiCalcThread::PiCalcThread()
{
    qDebug() << "Nit je kreirana\n";
}

PiCalcThread::~PiCalcThread()
{
    qDebug() << "Nit je unistena\n";
}

void PiCalcThread::run()
{
    qsrand(static_cast<unsigned>(QTime(0,0,0).secsTo(QTime::currentTime())));

    auto hits = 0.0, count = 0.0;

    while (hits < 100000000)
    {
        const double x = (qrand() % 512) / 512.0;
        const double y = (qrand() % 512) / 512.0;

        ++count;
        if (x * x + y * y <= 1)
        {
            ++hits;
        }
    }

    emit calculatedPi(4*hits/count);

}
