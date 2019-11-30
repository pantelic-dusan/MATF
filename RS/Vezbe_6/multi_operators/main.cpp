
#include <iostream>
#include <string>

template <typename T>
class equality_testable {
    public:
        bool operator==(const T& other) const {
            return !(static_cast<const T&>(*this) != other);
        }

        bool operator!=(const T& other) const {
            return !(static_cast<const T&>(*this) == other);
        }


};

class test: public equality_testable<test> {
    
    public:
        test(const std::string& data) :m_data(data) {

        }

        bool operator==(const test& other) const {
            return other.m_data == m_data;
        }
    private:
        std::string m_data;
};

int main()
{
    const test t1("data1"), t2("data2");

    // Sada sve radi kako bi trebalo
    std::cout << (t1 == t1) << std::endl;
    std::cout << (t1 != t1) << std::endl;
    std::cout << (t1 == t2) << std::endl;
    std::cout << (t1 != t2) << std::endl;

    return 0;
}