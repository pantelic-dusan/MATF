#include <iostream>
#include <string>
#include <vector>
#include <algorithm>

class value_gte {
public:
    value_gte(int comp):m_comp(comp) {

    }

    template <typename T>
    bool operator() (T value) const {
        return value >= m_comp;
    }

private:
    int m_comp;
};

int main(int argc, char const *argv[])
{
    
    value_gte gte42(42);

    std::cout << " 6 == 42 -> " << gte42(6)  << std::endl;
    std::cout << "42 == 42 -> " << gte42(42) << std::endl;

    std::vector<int> xs { 1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024 };

    std::cout << std::count_if(std::cbegin(xs), std::cend(xs),
                               gte42)
              << std::endl;



    int x = 42;
    const int& cx = x;
    [&] (auto y, auto z) { return x + y + z; };
}
