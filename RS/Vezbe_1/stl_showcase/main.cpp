#include <iostream>
#include <vector>
#include <set>
#include <map>
#include <forward_list>
#include <list>
#include <deque>

void show_help_and_quit();
void vector_showcase();
void set_showcase();
void map_showcase();
void forward_list_showcase();
void list_showcase();
void deque_showcase();

int main(int argc, char const *argv[]) {
    if (argc != 2) {
        show_help_and_quit();
    }

    const std::string option(argv[1]);

    if (option == "v") vector_showcase();
    else if (option == "s") set_showcase();
    else if (option == "m") map_showcase();
    else if (option == "fl") forward_list_showcase();
    else if (option == "l") list_showcase();
    else if (option == "d") deque_showcase();
    else show_help_and_quit();

    return 0;
}

void show_help_and_quit() {
    std::cerr << "Program se poziva naredbom: \"./stl-showcase opcija\"" << std::endl
              << std::endl
              << "Opcija mora biti jedna od: " << std::endl
              << "\tv   = Demonstracija rada kolekcije std::vector<T, ...>" << std::endl
              << "\ts   = Demonstracija rada kolekcije std::set<T, ...>" << std::endl
              << "\tm   = Demonstracija rada kolekcije std::map<Key, T, ...>" << std::endl
              << "\tfl  = Demonstracija rada kolekcije std::forward_list<T, ...>" << std::endl
              << "\tl   = Demonstracija rada kolekcije std::list<T, ...>" << std::endl
              << "\td   = Demonstracija rada kolekcije std::deque<T, ...>" << std::endl;
    exit(EXIT_FAILURE);
}

void vector_showcase() {
    std::vector<int> xs;
    int x;

    std::cout << "Unesite nekoliko celih brojeva: " << std::endl;
    while (std::cin >> x) {
        xs.push_back(x);
    }

    if (!xs.empty()) {
        std::cout << "Broj unetih brojeva: " << xs.size() << std::endl;
    } else {
        std::cout << "Nista nije uneto." << std::endl; 
    }

    std::cout << "Vrsi se dodavanje u vektor koristeci `insert`..." << std::endl;

    xs.insert(std::cbegin(xs), 17);
    xs.insert(std::cend(xs), 5, 0);

    std::cout << "Sadrzaj vektora:" << std::endl;
    for (const auto& x: xs) {
        std::cout << "-" << x << std::endl;
    }

    std::cout << "Prvi element u vektoru: " << xs[0] << std::endl;
    std::cout << "Poslednji element u vektoru: " << xs.back() << std::endl;

    std::cout << "Sadrzaj vektora koristeci cbegin:" << std::endl;
    for (auto iter = std::cbegin(xs); iter != std::cend(xs); iter++) {
        std::cout << *iter << " ";
    }
    std::cout << std::endl;

    xs.pop_back();
    xs.erase(std::cbegin(xs) + 3);

    std::cout << "Sadrzaj vektora koristeci crbegin:" << std::endl;
    for (auto iter = std::crbegin(xs); iter != std::crend(xs); iter++) {
        std::cout << *iter << " ";
    }
    std::cout << std::endl;
}

void set_showcase() {
    std::set<double> xs{1.0, 2.0, 3.0, -1.0, -4.3, -2.0, 5.0, -6.4};
    double x;

    std::cout << "xs: " << std::endl;
    for (const auto& x: xs)
        std::cout << x << " ";
    std::cout << std::endl;

    {
        std::cout << "\n[Pretraga broja]" << std::endl;
        std::cout << "Unesite broj za pretragu:" << std::endl;
        std::cin >> x;

        const auto iter = xs.find(x);
        if (iter != xs.cend()) {
            std::cout << "Pronadjen je element " << *iter << std::endl;
        } else {
            std::cout << "Nije pronadjen element " << x << std::endl;
        }
    }

    {
        std::cout << "\n[Pretraga prvog veceg]" << std::endl;
        std::cout << "Unesite broj za pretragu:" << std::endl;
        std::cin >> x;

        const auto iter = xs.upper_bound(x);
        if (iter != xs.cend()) {
            std::cout << "Pronadjen je prvi element " << *iter << " strogo veci od " << x << std::endl;
        }
        else {
            std::cout << "Nije pronadjen element strogo veci od " << x << std::endl;
        }
    }

    {
        std::cout << "\n[Pretraga prvog veceg ili jednakog]" << std::endl;
        std::cout << "Unesite broj za pretragu:" << std::endl;
        std::cin >> x;

        const auto iter = xs.lower_bound(x);
        if (iter != xs.cend()) {
            std::cout << "Pronadjen je prvi element " << *iter << " veci ili jednak od " << x << std::endl;
        }
        else {
            std::cout << "Nije pronadjen element veci ili jednak od " << x << std::endl;
        }
    }

    xs.insert({7.3, 5.0, 7.3, 3.0, 4.0});
    for (const auto& x: xs)
        std::cout << x << " ";
    std::cout << std::endl;
}

void map_showcase() {
    std::map<unsigned, std::string> students({
        {20100050u, "Nevena"},
        {20110145u, "Petar"},
        {20100043u, "Ana"}
    });

    unsigned key_existing = 20110145u;
    unsigned key_not_existing = 20000001u;

    std::cout << "Student sa indeksom " << key_existing
        << " se zove \"" << students[key_existing] << "\"" << std::endl;
    std::cout << "Student sa indeksom " << key_not_existing
        << " se zove \"" << students[key_not_existing] << "\"" << std::endl;

    std::cout << "Student sa indeksom " << key_existing
        << " se zove \"" << students.at(key_existing) << "\"" << std::endl;   

    const std::pair<unsigned, std::string> new_student(++key_not_existing, "Mihajlo");
    const std::pair<unsigned, std::string> new_student_with_same_key(key_not_existing, "Jelena");
    std::string state_of_input;

    {
        const auto input = students.insert(new_student);
        const auto iter = input.first;                      // uzimamo prvi element uredjenog para
        const auto successfull = input.second;
        if (successfull)
        {
            state_of_input = "Student je uspesno dodat.";
        }
        else
        {
            state_of_input = "Student nije uspesno dodat.";
        }
        std::cout
            << state_of_input << " Recnik sadrzi: ("
            << iter->first << ", " << iter->second << ")" << std::endl;
    }

    {
        const auto [iter, successfull] = students.insert(new_student_with_same_key);
        if (successfull)
        {
            state_of_input = "Student je uspesno dodat.";
        }
        else
        {
            state_of_input = "Student nije uspesno dodat.";
        }
        std::cout
            << state_of_input << " Recnik sadrzi: ("
            << iter->first << ", " << iter->second << ")" << std::endl;
    }

    {
        const auto [iter, successfull] = students.emplace(++key_not_existing, "Nikola");
        if (successfull)
        {
            state_of_input = "Student je uspesno dodat.";
        }
        else
        {
            state_of_input = "Student nije uspesno dodat.";
        }
        std::cout
            << state_of_input << " Recnik sadrzi: ("
            << iter->first << ", " << iter->second << ")" << std::endl;
    }

    {
        std::cout << "Adding: " << new_student_with_same_key.first << std::endl;
        const auto [iter, successfull] = students.insert_or_assign(
            new_student_with_same_key.first,
            new_student_with_same_key.second
        );
        if (successfull)
        {
            state_of_input = "Student je uspesno dodat.";
        }
        else
        {
            state_of_input = "Student je azuriran na ime " + new_student_with_same_key.second;
        }
        std::cout
            << state_of_input << " Recnik sadrzi: ("
            << iter->first << ", " << iter->second << ")" << std::endl;
    }




}

void forward_list_showcase() {
    std::forward_list<char> chars{'R', 'a', 'z', 'v', 'o', 'j', ' ', 'S', 'o', 'f', 't', 'v', 'e', 'r', 'a'};

    if (!chars.empty()) {
        std::cout << "Prvi element je: " << chars.front() << std::endl;
    }

    auto iter = chars.before_begin();

    std::cout << "Iterator before_begin "
              << ((++iter == chars.begin()) ? "jeste" : "nije")
              << " ispred begin iteratora" << std::endl;
    
    chars.insert_after(chars.cbefore_begin(), '>');

    for (const auto& ch: chars)
        std::cout << ch;
    std::cout << std::endl;

    chars.reverse();
    chars.insert_after(chars.cbefore_begin(), 3, '!');
    chars.reverse();

    for (const auto& ch: chars)
        std::cout << ch;
    std::cout << std::endl;
}

void list_showcase() {
    std::list<std::string> keywords{"if", "unsigned", "while", "void"};

    std::cout << "keywords:" << std::endl;
    for (const auto& keyword: keywords)
        std::cout << keyword << " " << std::endl;
    std::cout << std::endl;

    if (!keywords.empty()) {
        std::cout << "Broj elemenata u DPL: " << keywords.size() << std::endl;
        std::cout << "Prvi element DPL je: \"" << keywords.front() << "\"" << std::endl;
        std::cout << "Poslednji element DPL je: \"" << keywords.back() << "\"" << std::endl;
    }

    std::cout << "Dodaje se 'return' na pocetak." << std::endl;
    keywords.insert(keywords.cbegin(), "return");
    std::cout << "Dodaje se '>' na pocetak 3 puta." << std::endl;
    keywords.insert(keywords.cbegin(), 3, ">");
    std::cout << "Dodaje se '<' na kraj 3 puta." << std::endl;
    keywords.insert(keywords.cend(), 3, "<");

    std::cout << "Dodaje se '{' na pocetak." << std::endl;
    keywords.push_front("{");
    std::cout << "Dodaje se '}' na kraj." << std::endl;
    keywords.push_back("}");

    for (const auto& keyword: keywords)
        std::cout << keyword << " " << std::endl;
    std::cout << std::endl;
}

void deque_showcase() {
    std::deque<unsigned> xs{0, 1, 2, 3, 4, 5, 6, 7};

    for (const auto& x: xs)
    {
        std::cout << x << " ";
    }
    std::cout << std::endl;
}