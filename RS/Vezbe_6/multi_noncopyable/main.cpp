#include <string>
#include <iostream>

class non_copyable {
    public:
        non_copyable(const non_copyable& other) = delete;
        const non_copyable& operator=(const non_copyable& other) = delete;
    protected:
        non_copyable() = default;
        ~non_copyable() = default;
};

class database {
    public:
        database(const std::string url)
            :m_url(url) {
                std::cout << "Povezujem se sa bazom: " << url << std::endl;
        }

        ~database() {
            std::cout << "Raskidam konekciju sa bazom: " << m_url << std::endl;
        }

    private:
        std::string m_url;
};

class internal_database: public database, public non_copyable {
    public:
    internal_database()
        :database("http://localhost/") {

    }
};

int main()
{
    // Kopiranje "obicne" baze radi
    const database db1("http://sql.matf.bg.ac.rs/");
    const auto db1_copy = db1;

    internal_database db2;
    // Linija ispod ne prolazi: 
    // Kompilator javlja poruku da se pokusava kopiranje objekta
    // ciji je konstruktor kopije "izbrisan".
    //internal_database db2_copy = db2;

    return 0;
}