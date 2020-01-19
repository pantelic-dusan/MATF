#include <iostream>
#include <string>
#include <numeric>
#include <vector>

template <typename T>
int count_adj_equals(const T& xs) {

    return std::inner_product(
        std::begin(xs), std::end(xs)-1,
        std::begin(xs)+1,
        0,
        [](const int& x, const int& y){ return x+y; },
        [](const auto& x, const auto& y){ return x==y ? 1 : 0; }
    );
}

int main(int argc, char *argv[])
{
    const std::string text = "Hooloovoo";

    std::cerr << text << ": " << count_adj_equals(text) << std::endl;

    const std::vector<double> numbers{ -1.0, 2.36, 65.4, 65.4, 65.4, -1.0, 0.0, 5.4 };

    std::cerr << "numbers : " << count_adj_equals(numbers) << std::endl;

    return 0;
}