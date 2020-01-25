#include "generator.h"
#include <QTime>

Generator::Generator(int i): i(i)
{

}

void Generator::run() {
    qsrand(QTime(0,0,0).secsTo(QTime::currentTime()) + this->i);
    int numGenerations = qrand()%31+10;

    for (int j=0; j<numGenerations; j++) {
        char c = 'a' + (qrand()%26);
        emit letterGenerated(this->i, c);
        msleep(400);
    }

    emit generationComplete();
}
