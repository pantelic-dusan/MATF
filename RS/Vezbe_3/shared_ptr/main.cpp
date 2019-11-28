#include <memory>
#include <string>
#include <iostream>

struct example_object {
    example_object(const int number, const std::string& text)
        : m_number(number), m_text(text) {
        std::cout << "Kreiran je example_object(" << m_number << ", " << m_text << ")" << std::endl;
    }

    ~example_object() {
        std::cout << "Obrisan je example_object(" << m_number << ", " << m_text << ")" << std::endl;
    }

    int m_number;
    std::string m_text;
};

void test_shared_ptr_copy(const std::shared_ptr<example_object> p) {
    std::cout << "U funkciji test_shared_ptr_copy: " << p->m_number << " - " << p->m_text << std::endl;
}

int main() {

    const auto p1 = std::make_shared<example_object>(10, "Sadrzi 10");
    const auto p2(p1);
    const auto p3 = p2;

    std::cout << "Testiramo da li p1, p2 i p3 pokazuju na isti objekat..." << std::endl;
    std::cout << "p1: " << p1->m_number << " - " << p1->m_text << std::endl;
    std::cout << "p2: " << p2->m_number << " - " << p2->m_text << std::endl;
    std::cout << "p3: " << p3->m_number << " - " << p3->m_text << std::endl;

    std::cout << "Broj referenci na objekat: " << p1.use_count() << std::endl;

    {
        std::shared_ptr<example_object> p4 = p1;
        std::cout << "Broj referenci na objekat: " << p1.use_count() << std::endl;
    }

    std::cout << "Broj referenci na objekat: " << p1.use_count() << std::endl;

    std::cout << "Testiramo prosledjivanje pametnog pokazivaca kao argument funkcije..." << std::endl;

    test_shared_ptr_copy(p1);

    return 0;
}

