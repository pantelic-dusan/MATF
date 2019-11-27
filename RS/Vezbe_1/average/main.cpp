#include <iostream>
#include <vector>
#include <complex>
#include <numeric>

int main(int argc, char const *argv[])
{
    std::vector<std::complex<double>> numbers;
    std::complex<double> number;

    while (std::cin >> number) {
        numbers.push_back(number);
    }

    for (const auto& n:numbers) {
        std::cout << n << std::endl;
    }

    const auto sum = std::accumulate(numbers.cbegin(), numbers.cend(), std::complex<double>());

    std::cout << "Aritmeticka sredina: " << sum / std::complex<double>(numbers.size()) << std::endl;
    

    return 0;
}
