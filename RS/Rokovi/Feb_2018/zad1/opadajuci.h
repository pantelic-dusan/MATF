#ifndef opadajuci_H_
#define opadajuci_H_

// POCETAK_STUDENTSKOG_KODA
#include <algorithm>
#include <vector>
#include <string>
#include <iostream>

// KRAJ_STUDENTSKOG_KODA

std::vector<int> opadajuci(const std::vector<int>& xs)
{
    // POCETAK_STUDENTSKOG_KODA
    std::vector<int> rezultat;

    std::copy_if(std::begin(xs), std::end(xs), std::back_inserter(rezultat),
        [](auto num){
            std::string numStr = std::to_string(std::abs(num));
            return std::inner_product(std::begin(numStr), std::end(numStr)-1, std::begin(numStr)+1,
              true, [](auto a, auto b){ return a&&b; },
              [](auto a, auto b){ return a>=b; }  
            );
        }
    );    

    // for (auto x: xs) {
    //     bool dobar = true;

    //     int tmp = x;
    //     if (tmp < 0) tmp = -tmp;

    //     int prethodna_cifra = 0;

    //     while (tmp > 0) {
    //         if (tmp % 10 < prethodna_cifra) {
    //             dobar = false;
    //             break;
    //         }

    //         prethodna_cifra = tmp % 10;
    //         tmp = tmp / 10;
    //     }

    //     if (dobar) {
    //         rezultat.push_back(x);
    //     }
    // }

    return rezultat;
    // KRAJ_STUDENTSKOG_KODA
}

#endif /* opadajuci_H_ */
