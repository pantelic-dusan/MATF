#include <vector>
#include <iostream>
#include <algorithm>
#include <iterator>
#include <string>

bool is_selected(const std::string &item) {
    return item[0] == '*';
}

template <typename It, typename Pred>
std::pair<It, It> move_selection(It first, It last, It dest, Pred predicate) {
    return {
        std::stable_partition(first, dest, 
            [predicate](const auto &item){return !predicate(item);}),
        std::stable_partition(dest, last,
            [predicate](const auto &item){ return predicate(item);})
    };
}

int main(int argc, char const *argv[])
{
    std::vector<std::string> items {
        "aardvark",
        "compunctious",
        "**congratulations**",
        "contrafribblarity",
        "contrary",
        "dictionary",
        "**interphrastical**",
        "**patronise**",
        "**pericombobulation**",
        "phrasmotic",
        "**syllables**"
    };

    move_selection(
            std::begin(items), std::end(items), std::begin(items) + 5,
            is_selected
    );

    std::copy(std::begin(items), std::end(items),
        std::ostream_iterator<std::string>(std::cout, "\n"));

    return 0;
}
