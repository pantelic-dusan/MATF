#include <iostream>
#include <string>
#include <numeric>
#include <vector>

template <typename T>
int equal_to_int(const T& left, const T& right) {
    return left==right?1:0;
}

template <typename T>
T plus(const T& left, const T& right) {
    return left+right;
}

template <typename T, typename Inner = typename T::value_type>
int count_adj_equals(const T& xs) {
    return std::inner_product(std::begin(xs), std::end(xs)-1
        , std::begin(xs)+1
        , 0
        , plus<int>
        , equal_to_int<Inner>

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