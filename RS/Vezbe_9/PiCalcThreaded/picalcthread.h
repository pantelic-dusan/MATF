#ifndef PICALCTHREAD_H
#define PICALCTHREAD_H

#include <QThread>

class PiCalcThread : public QThread
{
    Q_OBJECT

public:
    PiCalcThread();
    ~PiCalcThread();

Q_SIGNALS:
    void calculatedPi(double value);

protected:
    void run() override;
};

#endif // PICALCTHREAD_H
