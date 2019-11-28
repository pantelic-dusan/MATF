#include<cstdio>

int foo_c() {
    int return_value = 0;

    FILE *file = fopen("data.txt", "r");

    if (file != NULL) {
        int number;
        while (fscanf(file, "%d", &number) != EOF) {
            if (number < 0)
            {
                return_value = 1;
                break;
            }
            printf("%d ", number);
        }
        printf("\n");
    } else {
        return_value = 1;
    }

    fclose(file);

    return return_value;

}

#include <iostream>
#include <fstream>
#include <string>

class[[nodiscard]] my_file
{
public:
    my_file()
    {
        // Inicijalizacija resursa se vrsi u konstruktoru
        m_file = std::ifstream("data.txt");
        std::cout << "Datoteka je " << (m_file.is_open() ? "uspesno" : "neuspesno") << " otvorena" << std::endl;
    }

    ~my_file()
    {
        // Deinicijalizacija resursa se vrsi u destruktoru
        m_file.close();
        std::cout << "Datoteka je uspesno zatvorena" << std::endl;
    }

    std::ifstream m_file;
};

void foo_cpp()
{
    my_file file;

    int number;
    while (file.m_file >> number)
    {
        if (number < 0)
        {
            std::cout << std::endl;
            // Doslo je do greske, prijavi (ispali) izuzetak u izvrsavanju programa.
            // Izuzetak moze biti (skoro) bilo koja vrednost koja moze da se kopira.
            throw std::string("Neki izuzetak");
        }
        std::cout << number << " ";
    }
    std::cout << std::endl;
}

int main()
{
    std::cout << "Testiramo nacin implementacije u programskom jeziku C..." << std::endl;
    int return_value = foo_c();
    if (return_value == 0)
    {
        std::cout << "C funkcija se zavrsila uspesno" << std::endl;
    }
    else
    {
        std::cout << "C funkcija se zavrsila neuspesno" << std::endl;
    }

    std::cout << std::endl << "Testiramo nacin implementacije u programskom jeziku C++..." << std::endl;
    // Pokusavamo da izvrsavamo problematicnu funkciju
    try
    {
        foo_cpp();
        std::cout << "C++ funkcija se zavrsila uspesno" << std::endl;
    }
    // Hvatamo izuzetak koji je ispaljen na osnovu njegovog tipa.
    // S obzirom da se izuzetak kopira prilikom ispaljivanja,
    // ako ga ne uhvatimo po referenci, imacemo duplo kopiranje.
    catch (const std::string & error_str)
    {
        std::cout << "C++ funkcija se zavrsila neuspesno: " << error_str << std::endl;
    }
}