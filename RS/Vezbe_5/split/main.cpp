#include <string>
#include <vector>
#include <algorithm>
#include <iostream>
#include <iterator>

std::vector<std::string> split(const std::string& str) {
    std::vector<std::string> result;
    auto word_begin = str.cbegin();
    while (word_begin != str.cend()) {
        word_begin = std::find_if_not(word_begin, str.cend(), isspace);

        const auto work_end = std::find_if(word_begin, str.cend(), isspace);

        if (word_begin != str.cend()) {
            result.emplace_back(word_begin, work_end);
        }

        word_begin = work_end;
    }

    return result;
}

int main(int argc, const char *argv[]) {
    const auto message = split("   Splitting        atoms     or      strings              ");

    std::copy(message.cbegin(), message.cend(), 
              std::ostream_iterator<std::string>(std::cout, "\n"));

    return 0;
}