#include <iostream>
#include <string>
#include <vector>
#include <algorithm>

int main(int argc, char const *argv[])
{
    
    auto val = 42;
    auto gte42 = [base=val](auto value){ return value >= base; };

    std::cout << " 6 == 42 -> " << gte42(6)  << std::endl;
    std::cout << "42 == 42 -> " << gte42(42) << std::endl;

    std::vector<float> xs { 1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024 };

    std::cout << std::count_if(std::cbegin(xs), std::cend(xs),
                               gte42)
              << std::endl;
    
    return 0;
}
