#include <vector>
#include <iostream>
#include <algorithm>
#include <iterator>
#include <string>

bool is_selected(const std::string &item)
{
    return item[0] == '*';
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

    items.erase(
        std::remove_if(std::begin(items), std::end(items),
            [](const auto &item){ return !is_selected(item);}),
        std::end(items)
    );

    std::copy(std::begin(items), std::end(items),
              std::ostream_iterator<std::string>(std::cout, "\n"));
    
    return 0;
}
