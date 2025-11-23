#ifndef UTILS_H
#define UTILS_H

#include <iostream>
#include <omp.h>

void fillRandom(float* array, int totalDim){
#pragma omp parallel for
  for (int i = 0; i < totalDim; i++) {
    array[i] = rand() % 10;
  }
}

void initialize(float* array, int totalDim){
#pragma omp parallel for
  for (int i = 0; i < totalDim; i++) {
    array[i] = 0;
  }
}


class Ref{
  public:
    static const int M = 4;
    static const int N = 2;
    static const int K = 3;

    float A[M * K] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    float B[K * N] = {1, 2, 3, 4, 5, 6};
    float C[M * N] = {22, 28, 49, 64, 76, 100, 103, 136};

    bool checkRef(float* newC){
      for (int i = 0; i < M * N; i++) {
        if (newC[i] != C[i]) {
          std::cout << "fail at: " << i << ", values: " << newC[i] << " vs. " << C[i] << std::endl;
          return false;
        }
      }
      return true;
    }
};

#endif //UTILS_H
