
#include <algorithm>
#include <fstream>
#include <iostream>
#include <string>
#include <tuple>
#include <vector>

#include <cassert>

class transformator {
public:

    enum class TipTransform {
        uVelika, uMala
    };
    // POCETAK_STUDENTSKOG_KODA
    transformator(TipTransform t)
        : m_transformacija(t)
    {
    }

    std::string operator()(const std::string& str) {

        if (str == m_original) {
            return m_transformisan;
        }

        std::string result = str;
        if (m_transformacija == TipTransform::uMala) {
            std::transform(std::begin(str), std::end(str), std::begin(result), tolower);
        } else if (m_transformacija == TipTransform::uVelika) {
            std::transform(std::begin(str), std::end(str), std::begin(result), toupper);
        } else {
            throw "Greska";
        }

        m_original = str;
        m_transformisan = result;

        return result;

    }

    // KRAJ_STUDENTSKOG_KODA

    // POCETAK_STUDENTSKOG_KODA
    TipTransform m_transformacija;
    // KRAJ_STUDENTSKOG_KODA

    std::string m_original;
    std::string m_transformisan;
};



int main(int argc, char *argv[])
{
    // POCETAK_STUDENTSKOG_KODA
    transformator u_velika(transformator::TipTransform::uVelika);
    transformator u_mala(transformator::TipTransform::uMala);

    assert(u_velika("Hello") == "HELLO");
    assert(u_mala("Hello") == "hello");
    // KRAJ_STUDENTSKOG_KODA

    std::cout << "Tests passed" << std::endl;

    return 0;
}
