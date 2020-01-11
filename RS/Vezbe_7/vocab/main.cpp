#include <string>
#include <iostream>
#include <map>
#include <algorithm>
#include <iterator>
#include <fstream>

void string_to_lower(std::string & str) {
    std::transform(str.begin(), str.end(), str.begin(), tolower);
}

class compare_ignorecase {
    public:
        bool operator() (std::string left, std::string right) const {
            string_to_lower(left);
            string_to_lower(right);

            return left < right;
        }
};

int main() {

    std::map<std::string, std::string, compare_ignorecase> words;

    std::ifstream in("data/words.txt");

    std::string word;
    std::string definition;

    while (in >> word) {
        std::getline(in, definition);
        words[word] = definition;
    }

    std::string query;
    std::cout << "Unesite rec za pretragu: ";

    while (std::cin >> query) {

        auto lower = words.lower_bound(query);

        (*query.rbegin())++;
        auto upper = words.lower_bound(query);

        for (; lower != upper; ++lower) {
            std::cout << lower->first << " -> " << lower->second << std::endl;
        }

        std::cout << "Unesite rec za pretragu: ";
    }
}