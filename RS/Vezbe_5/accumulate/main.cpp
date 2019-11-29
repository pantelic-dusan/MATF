
#include <vector>
#include <numeric>
#include <iostream>
#include <string>

std::string append_if_odd(const std::string& acc, const int x) {
    return x%2!=0?
        (acc+std::to_string(x)):acc;
}

int main() {
    const std::vector<int> xs{1,2,3,4,5};

    std::cout << std::accumulate(std::cbegin(xs), std::cend(xs),
        std::string(),
        append_if_odd) << std::endl;
    
    return 0;
}