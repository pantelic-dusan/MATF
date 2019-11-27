#include <iostream>
#include <string>
#include <vector>
#include <algorithm>

int main() {

    std::vector<std::string> messages;

    size_t maximum_length = 0;

    std::string line;
    while (getline(std::cin, line)) {
        messages.push_back(line);
        line.clear();
        const size_t line_length = messages.back().size();
        maximum_length = line_length>maximum_length?line_length:maximum_length;
    }

    const std::string bar(maximum_length+4, '*');
    std::cout << bar << std::endl;

    for (const auto& line: messages) {
        std::cout
            << "* " << line
            << std::string(maximum_length - line.size(), ' ')
            << " *" << std::endl;
    }

    std::cout << bar << std::endl;

    return 0;
}