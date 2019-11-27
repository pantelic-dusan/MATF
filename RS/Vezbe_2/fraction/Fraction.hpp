#ifndef FRACTION_HPP
#define FRACTION_HPP

#include <iostream>

class Fraction {
    public:
        Fraction(int numerator=0, unsigned int denominator=1);

        int numerator() const;
        unsigned int denominator() const;

        void set_numerator(int numerator);
        void set_denominator(unsigned int denominator);

        Fraction operator+(const Fraction& other) const;
        Fraction operator-(const Fraction& other) const;
        Fraction operator*(const Fraction& other) const;
        Fraction operator/(const Fraction& other) const;

        Fraction operator++(int);

        Fraction& operator++();

        Fraction operator--(int);

        Fraction& operator--();

        Fraction operator-() const;

        operator double() const;

        bool operator==(const Fraction& other) const;
        bool operator!=(const Fraction& other) const;
        bool operator>(const Fraction& other) const;
        bool operator<(const Fraction& other) const;
        bool operator>=(const Fraction& other) const;
        bool operator<=(const Fraction& other) const;

        Fraction operator*(int num) const;
        Fraction operator/(int num) const;

    private:
        int m_numerator;
        unsigned int m_denominator;

        friend std::ostream& operator<<(std::ostream& out, const Fraction& value);
        friend std::istream& operator>>(std::istream& in, Fraction& value);

        void reduce_fraction();
};

std::ostream& operator<<(std::ostream& out, const Fraction& value);
std::istream& operator>>(std::istream& in, Fraction& value);

#endif