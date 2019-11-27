#include "Fraction.hpp"

Fraction::Fraction(int numerator, unsigned int denominator)
    : m_numerator(numerator), m_denominator(denominator)
{
    reduce_fraction();
}

void Fraction::reduce_fraction() {
    int a = m_numerator;
    if (a < 0)
        a = -a;
    int b = static_cast<int>(m_denominator);
    int pom = b;
    while (b != 0)
    {
        pom = b;
        b = a % b;
        a = pom;
    }
    m_numerator /= a;
    m_denominator /= a;
    
}

int Fraction::numerator() const {
    return m_numerator;
}

unsigned int Fraction::denominator() const {
    return m_denominator;
}

void Fraction::set_numerator(int numerator) {
    m_numerator = numerator;
}

void Fraction::set_denominator(unsigned int denominator) {
    m_denominator = denominator;
}

Fraction Fraction::operator+(const Fraction& other) const {
    return Fraction(
        m_denominator*other.m_numerator + other.m_denominator*m_numerator,
        m_denominator*other.m_numerator
    );
}

Fraction Fraction::operator-(const Fraction& other) const {
    return Fraction(
        other.m_denominator*m_numerator - m_denominator*other.m_numerator,
        m_denominator*other.m_denominator
    );
}

Fraction Fraction::operator*(const Fraction& other) const {
    return Fraction(
        m_numerator*other.m_numerator,
        m_denominator*other.m_denominator
    );
}

Fraction Fraction::operator/(const Fraction& other) const {
    return Fraction(
        m_numerator*other.m_denominator,
        m_denominator*other.m_numerator
    );
}

Fraction Fraction::operator++(int) {
    Fraction tmp(m_numerator, m_denominator);
    ++(*this);
    return tmp;
}

Fraction& Fraction::operator++() {
    m_numerator += m_denominator;
    return (*this);
}

Fraction Fraction::operator--(int) {
    Fraction tmp(m_numerator, m_denominator);
    --(*this);
    return tmp;
}

Fraction& Fraction::operator--() {
    m_numerator -= m_denominator;
    return (*this);
}

Fraction::operator double() const {
    return m_numerator/static_cast<double>(m_denominator);
}

bool Fraction::operator==(const Fraction& other) const {
    return m_numerator*other.m_denominator == other.m_numerator*m_denominator;
}

bool Fraction::operator!=(const Fraction& other) const {
    return !(*this==other);
}

bool Fraction::operator>(const Fraction& other) const {
    return m_numerator*other.m_denominator > other.m_numerator*m_denominator;
}

bool Fraction::operator<=(const Fraction& other) const {
    return !(*this>other);
}

bool Fraction::operator<(const Fraction& other) const {
    return m_numerator*other.m_denominator < other.m_numerator*m_denominator;
}

bool Fraction::operator>=(const Fraction& other) const {
    return !(*this<other);
}

std::ostream& operator<<(std::ostream& out, const Fraction& value) {
    return out << value.m_numerator << "/" << value.m_denominator;
}

std::istream& operator>>(std::istream& in, Fraction& value) {
    char c;
    return in >> value.m_numerator >> c >> value.m_denominator;
}
