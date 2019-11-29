#include <vector>
#include <iostream>
#include <algorithm>
#include <iterator>

bool div_by_3_remainder_0(const int x)
{
    return x % 3 == 0;
}

bool div_by_3_remainder_1(const int x)
{
    return x % 3 == 1;
}

int main(int argc, char *argv[]) {
    std::vector<int> xs { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 };

    const auto first_end = std::stable_partition(std::begin(xs),
        std::end(xs), div_by_3_remainder_0);

    const auto second_end = std::stable_partition(first_end, 
        std::end(xs), div_by_3_remainder_1);

    std::copy(std::begin(xs), std::end(xs),
        std::ostream_iterator<int>(std::cout, " "));

    return 0;
}