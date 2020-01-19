#include <vector>
#include <iostream>
#include <algorithm>
#include <numeric>

template <typename T>
auto length(const T &xs) {
    return std::distance(std::begin(xs), std::end(xs));
}

template <typename T>
auto avg_1(const T &xs) {
    int result = 0;
    for (auto x: xs) {
        result += x;
    }

    return result/static_cast<float>(xs.size());
}

template <typename T>
auto avg_2(const T &xs) {
    return std::accumulate(std::begin(xs), std::end(xs), 0)/static_cast<float>(length(xs));
}

template <typename T>
auto avg_3(const T &xs) {
    return std::reduce(xs.begin(), xs.end(), 0)/static_cast<float>(length(xs));
}

int main(int argc, char const *argv[])
{
    std::vector<int> xs { 1, 2, 3, 4, 5, 6, 7, 8 };

    std::cout << avg_1(xs) << std::endl;
    std::cout << avg_2(xs) << std::endl;
    std::cout << avg_3(xs) << std::endl;

    return 0;
}
