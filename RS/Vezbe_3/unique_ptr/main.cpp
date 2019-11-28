#include <memory>
#include <string>
#include <iostream>

struct example_object
{
    example_object(const int number, const std::string & text)
        : m_number(number), m_text(text)
    {
        std::cout << "Kreiran je example_object(" << m_number << ", " << m_text << ")" << std::endl;
    }

    ~example_object()
    {
        std::cout << "Obrisan je example_object(" << m_number << ", " << m_text << ")" << std::endl;
    }

    int m_number;
    std::string m_text;
};

void test_ownership_change(const std::unique_ptr<example_object> p) {
    std::cout << "U funkciji test_ownership_change: " << p->m_number << " - " << p->m_text << std::endl;
}

void test_ref_forward(const std::unique_ptr<example_object>& p) {
    std::cout << "U funkciji test_ref_forward: " << p->m_number << " - " << p->m_text << std::endl;
}

int main() {

    auto p1 = std::make_unique<example_object>(10, "Sadrzi 10");

    // const auto p2(p1); error
    // const auto p3 = p2; error

    std::cout << "p1: " << p1->m_number << " - " << p1->m_text << std::endl;

    std::cout << std::endl;
    std::cout << "Testiramo da li je vlasnistvo prebaceno sa p1 na p2..." << std::endl;

    auto p2(std::move(p1));

    if (p1 != nullptr)
    {
        std::cout << "p1: " << p1->m_number << " - " << p1->m_text << std::endl;
    }
    else if (p2 != nullptr)
    {
        std::cout << "p2: " << p2->m_number << " - " << p2->m_text << std::endl;
    }
    else
    {
        std::cout << "Ni p1 ni p2 nemaju vlasnistvo nad dinamickim objektom" << std::endl;
    }

    auto p3 = std::move(p2);

    std::cout << std::endl;
    std::cout << "Testiramo prosledjivanje pametnog pokazivaca kao argument funkcije..." << std::endl;

    test_ownership_change(std::move(p3));
    
    if (p3 != nullptr)
    {
        std::cout << "p3: " << p3->m_number << " - " << p3->m_text << std::endl;
    }
    else
    {
        std::cout << "p3 je izgubio vlasnistvo nad dinamickim objektom" << std::endl;
    }

    std::cout << std::endl;
    std::cout << "Testiramo promenu vlasnistva pametnog pokazivaca na neki drugi objekat..." << std::endl;

    p3 = std::make_unique<example_object>(15, "Sadrzi 15");
    std::cout << "p3: " << p3->m_number << " - " << p3->m_text << std::endl;

    std::cout << std::endl;
    std::cout << "Testiramo promenu vlasnistva pametnog pokazivaca u funkciji kada se on prenosi po referenci..." << std::endl;

    test_ref_forward(p3);

    if (p3 != nullptr)
    {
        std::cout << "p3: " << p3->m_number << " - " << p3->m_text << std::endl;
    }
    else
    {
        std::cout << "p3 je izgubio vlasnistvo nad dinamickim objektom" << std::endl;
    }

    example_object *raw_ptr = p3.get();
    
    return 0;
}
