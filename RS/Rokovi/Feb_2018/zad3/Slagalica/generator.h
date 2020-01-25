#ifndef GENERATOR_H
#define GENERATOR_H

#include <QThread>

class Generator : public QThread
{
    Q_OBJECT

public:
    Generator(int i);

Q_SIGNALS:
    void letterGenerated(int i, char c);
    void generationComplete();

public:
    void run() override;

private:
    int i;
};

#endif // GENERATOR_H
