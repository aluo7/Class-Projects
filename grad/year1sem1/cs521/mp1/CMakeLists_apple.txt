cmake_minimum_required(VERSION 3.16 FATAL_ERROR)
project(cs521_mp1 LANGUAGES CXX)

set(CMAKE_CXX_STANDARD 14)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

if(APPLE)
    # Homebrew LLVM install prefix
    set(LLVM_HOME "/opt/homebrew/opt/llvm")

    # Force use of Homebrew LLVM compilers
    set(CMAKE_C_COMPILER "${LLVM_HOME}/bin/clang" CACHE STRING "" FORCE)
    set(CMAKE_CXX_COMPILER "${LLVM_HOME}/bin/clang++" CACHE STRING "" FORCE)

    # Add Homebrew LLVM headers and libs
    include_directories(${LLVM_HOME}/include)
    link_directories(${LLVM_HOME}/lib)

    # Tell CMake where to find OpenMP inside Homebrew LLVM
    set(OpenMP_CXX_FLAGS "-fopenmp -O3")
    set(OpenMP_CXX_LIB_NAMES "omp")
    set(OpenMP_omp_LIBRARY "${LLVM_HOME}/lib/libomp.dylib")

    message(STATUS "Using Homebrew LLVM from ${LLVM_HOME}")
else()
    # Fallback for Linux
    find_package(OpenMP REQUIRED)
    if(OpenMP_CXX_FOUND)
        set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} ${OpenMP_CXX_FLAGS}")
        set(CMAKE_EXE_LINKER_FLAGS "${CMAKE_EXE_LINKER_FLAGS} ${OpenMP_EXE_LINKER_FLAGS}")
    else()
        message(FATAL_ERROR "Need OpenMP")
    endif()
endif()

add_executable(mp1_cpu ../cpu/gemm_cpu.cpp)

if(APPLE)
    target_link_libraries(mp1_cpu PRIVATE ${OpenMP_omp_LIBRARY})
else()
    target_link_libraries(mp1_cpu PRIVATE OpenMP::OpenMP_CXX)
endif()