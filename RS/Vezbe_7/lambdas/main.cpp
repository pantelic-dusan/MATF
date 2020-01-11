
#include <vector>
#include <algorithm>
#include <iostream>
#include <functional>

class Value {
    public:
        Value(const int value) :value(value) {

        }

        bool odd() const {
            return value%2 == 1;
        }

        bool even() const {
            return value%2 == 0;
        }

        bool between(const int min, const int max) const {
            return value >= min && value <= max;
        }

        const int value;
};

int main(int argc, char const *argv[]) {
    
    using std::placeholders::_1;

    std::vector<Value> values {1,2,3,4,5};

    const auto f1 = std::find_if(values.cbegin(), values.cend(),
        [](const Value& value) {return value.even();}
    );

    const auto limit = 3;
    const auto f2 = std::find_if(values.cbegin(), values.cend(),
        [limit](const Value& value){return value.value > limit;}
    );

    const auto f3 = std::find_if(values.cbegin(), values.cend(),
        [l = limit+42](const Value& value){return value.value > l;}
    );

    const auto f4 = std::find_if(values.cbegin(), values.cend(),
        std::bind(&Value::between, _1,2,4)
    );

    if (f1 != values.cend())
    {
        std::cout << "f1: " << (f1->value) << std::endl;
    }
    if (f2 != values.cend())
    {
        std::cout << "f2: " << (f2->value) << std::endl;
    }
    if (f3 != values.cend())
    {
        std::cout << "f3: " << (f3->value) << std::endl;
    }
    if (f4 != values.cend())
    {
        std::cout << "f4: " << (f4->value) << std::endl;
    }


    return 0;
}
