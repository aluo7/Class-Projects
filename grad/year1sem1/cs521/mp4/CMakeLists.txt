cmake_minimum_required(VERSION 3.14)
project(Jax4AutomaticDifferentiation LANGUAGES CXX)

find_package(GTest REQUIRED)
add_executable(Jax4AutomaticDifferentiation dual_test.cpp)
target_link_libraries(Jax4AutomaticDifferentiation GTest::gtest_main pthread)
add_test(NAME DualTests COMMAND Jax4AutomaticDifferentiation)
