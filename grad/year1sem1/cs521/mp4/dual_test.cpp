#include "dual_num.h"
#include <gtest/gtest.h>
#include <chrono>
#include <iostream>

TEST(DualNumber, Basic) {
    dual_num x(3.0f, 1.0f);
    auto y = sin(x);
    EXPECT_NEAR(y.primal(), std::sin(3.0f));
    EXPECT_NEAR(y.dual(), std::cos(3.0f));
}

TEST(DualVector, Vector) {
    dual_vector x(4, dual_num(2.0f, 1.0f));
    auto z = sin(x);
    EXPECT_EQ(z.size(), 4);
}

// Many more tests for all ops

// Overhead measurement (run 1e6 runs)
int main(int argc, char** argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}
