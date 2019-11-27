#include <iostream>
#include <vector>
#include <algorithm>
#include <iterator>

int main() {
    std::vector<int> xs;

    std::copy(
        std::istream_iterator<int>(std::cin),
        std::istream_iterator<int>(),
        std::back_inserter(xs)
    );

    std::sort(xs.begin()+1, xs.end()-1);

    std::copy(xs.cbegin(), xs.cend(), std::ostream_iterator<int>(std::cout, " , "));
    std::cout << std::endl;

    std::copy(xs.crbegin(), xs.crend(), std::ostream_iterator<int>(std::cout, " , "));

    return 0;
}