#include <iostream>
#include <algorithm>
#include <string>

bool is_palindrome(const std::string& str) {
    return std::equal(str.cbegin(), str.cend(), str.crbegin());
}


int main(int argc, const char *argv[])
{
    if (argc != 2) {
        std::cout << "Usage: " << argv[0] << " rec" << std::endl;
        return 1;
    }

    std::cout << argv[1] <<
        (is_palindrome(argv[1]) ? " jeste ":" nije ")
        << "palindrom"
        << std::endl;

    return 0;
}