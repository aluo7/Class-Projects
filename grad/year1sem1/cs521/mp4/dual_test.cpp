#include "dual_number.h"
#include <gtest/gtest.h>
#include <chrono>
#include <iostream>

TEST(DualNumber, Constructors) {
    dual_number a;                EXPECT_FLOAT_EQ(a.value(), 0.0f); EXPECT_FLOAT_EQ(a.dual(), 0.0f);
    dual_number b(4.2f);          EXPECT_FLOAT_EQ(b.value(), 4.2f); EXPECT_FLOAT_EQ(b.dual(), 0.0f);
    dual_number c(4.2f, 1.5f);   EXPECT_FLOAT_EQ(c.value(), 4.2f); EXPECT_FLOAT_EQ(c.dual(), 1.5f);
}

TEST(DualNumber, Arithmetic) {
    dual_number x(3.0f, 1.0f);
    dual_number y(4.0f, 0.0f);
    auto z = x + y - x * y;
    EXPECT_FLOAT_EQ(z.value(), 3.0f + 4.0f - 12.0f);
    EXPECT_FLOAT_EQ(z.dual(), 1.0f + 0.0f - (3.0f*0.0f + 1.0f*4.0f));
}

TEST(DualNumber, Functions) {
    dual_number x(0.0f, 1.0f);
    EXPECT_FLOAT_EQ(sin(x).value(), 0.0f);
    EXPECT_FLOAT_EQ(sin(x).dual(), 1.0f);      // cos(0) = 1

    EXPECT_FLOAT_EQ(cos(x).value(), 1.0f);
    EXPECT_FLOAT_EQ(cos(x).dual(), 0.0f);

    EXPECT_FLOAT_EQ(exp(dual_number(1.0f, 1.0f)).dual(), std::exp(1.0f));
    EXPECT_FLOAT_EQ(ln(dual_number(2.0f, 1.0f)).dual(), 0.5f);
    EXPECT_FLOAT_EQ(sigmoid(dual_number(0.0f, 1.0f)).dual(), 0.25f);
}

TEST(DualVector, Elementwise) {
    dual_vector v = {dual_number(0.0f, 1.0f), dual_number(1.0f, 1.0f)};
    auto sv = sin(v);
    EXPECT_FLOAT_EQ(sv[0].value(), 0.0f);
    EXPECT_FLOAT_EQ(sv[0].dual(), 1.0f);
    EXPECT_NEAR(sv[1].value(), std::sin(1.0f), 1e-6);
}

TEST(DualComposed, ComposedFunction) {
    // f(x) = sin(exp(x² + cos(x))) at x = 1.0, seed = 1.0
    dual_number x(1.0f, 1.0f);
    auto y = sin(exp(x*x + cos(x)));
    std::cout << "Composed example: " << y << "\n";
}

int main(int argc, char** argv) {
    ::testing::InitGoogleTest(&argc, argv);
    int ret = RUN_ALL_TESTS();

    std::cout << "\n--- BENCHMARK DATA ---\n";
    std::cout << "N,Float_us,Dual_us,Overhead\n";

    // Run benchmark for powers of 10
    std::vector<int> sizes = {10000, 100000, 1000000, 10000000, 50000000};

    for (int N : sizes) {
        // Benchmark Plain Floats
        volatile float sink_f = 0.0f;
        auto t1 = std::chrono::high_resolution_clock::now();
        for (int i = 0; i < N; ++i) {
            float x = i * 0.0001f;
            sink_f += std::tanh(std::exp(std::sin(x * x + 3.0f)));
        }
        auto t2 = std::chrono::high_resolution_clock::now();
        long float_us = std::chrono::duration_cast<std::chrono::microseconds>(t2 - t1).count();

        // Benchmark Dual Numbers
        volatile float sink_d = 0.0f;
        auto t3 = std::chrono::high_resolution_clock::now();
        for (int i = 0; i < N; ++i) {
            dual_number x(i * 0.0001f, 1.0f);
            auto y = tanh(exp(sin(x*x + dual_number(3.0f))));
            sink_d += y.value();
        }
        auto t4 = std::chrono::high_resolution_clock::now();
        long dual_us = std::chrono::duration_cast<std::chrono::microseconds>(t4 - t3).count();

        double overhead = (double)dual_us / float_us;
        std::cout << N << "," << float_us << "," << dual_us << "," << overhead << "\n";
    }

    return ret;
}
