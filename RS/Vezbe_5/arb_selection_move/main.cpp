#include <vector>
#include <iostream>
#include <algorithm>
#include <iterator>
#include <string>
#include <functional>

bool is_selected(const std::string& item) {
    return item[0]=='*';
}

template <typename Pred, typename Value>
bool not_predicate(Pred predicate, const Value& val) {
    return !predicate(val);
}

template<typename It, typename Pred>
void move_selection(It first, It last, It destination, Pred predicate) {

    std::stable_partition(first, destination,
        std::bind(not_predicate<Pred, decltype(*first)>, predicate, std::placeholders::_1)  
    );

    std::stable_partition(destination, last, predicate);
}

int main(int argc, char *argv[])
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

    move_selection(std::begin(items), std::end(items), std::begin(items) + 5,
                   is_selected);

    std::copy(std::begin(items), std::end(items),
              std::ostream_iterator<std::string>(std::cout, "\n"));

    return 0;
}