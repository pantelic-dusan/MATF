
#define CATCH_CONFIG_MAIN
#include "catch2/catch.hpp"

#include "opadajuci.h"

#include <vector>

TEST_CASE("Equality testing", "[equality]") {
    // POCETAK_STUDENTSKOG_KODA
    SECTION("Opsti test") {
        REQUIRE(
            opadajuci(std::vector<int>{1, 20, 13}) == std::vector<int>{1, 20}
        );

        REQUIRE(
            opadajuci(std::vector<int>{11, 20, 13}) == std::vector<int>{11, 20}
        );
    }

    SECTION("Svi dobri") {
        REQUIRE(
            opadajuci(std::vector<int>{12, 23, 34}) == std::vector<int>{}
        );

        REQUIRE(
            opadajuci(std::vector<int>{-12, -23, -34}) == std::vector<int>{}
        );
    }

    SECTION("Svi manji") {
        REQUIRE(
            opadajuci(std::vector<int>{21, 32, 43}) == std::vector<int>{21, 32, 43}
        );

        REQUIRE(
            opadajuci(std::vector<int>{-21, -32, -43}) == std::vector<int>{-21, -32, -43}
        );
    }

    SECTION("Svi isti") {
        REQUIRE(
            opadajuci(std::vector<int>{11, 0, 222}) == std::vector<int>{11, 0, 222}
        );

        REQUIRE(
            opadajuci(std::vector<int>{-11, 0, -222}) == std::vector<int>{-11, 0, -222}
        );
    }


    // KRAJ_STUDENTSKOG_KODA
}

