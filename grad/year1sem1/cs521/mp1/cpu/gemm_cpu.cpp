#include <chrono>
#include <algorithm>
#include "../include/utils.h"

#define NUM_RUNS 2
#define TILE_SIZE 32

#define CHECK(name) std::cout << "checking " << #name << std::endl;								\
  initialize(refC, Ref::M * Ref::N);				                    						\
  name(ref.A, ref.B, refC, Ref::M, Ref::N, Ref::K);		                						\
  if (!ref.checkRef(refC)){				                             							\
    std::cerr << #name << ": check ref failed!" << std::endl;        							\
  };
  
#define TIME(name)                                                      						\
  for (int i = 0; i < 1; i++)  							                 						\
    {							                        						         		\
      name(A, B, C, M, N, K);		                       										\
    }								                                							\
  std::chrono::duration<double, std::milli> time_##name(0);     								\
  for (int i = 0; i < NUM_RUNS; i++)															\
    {																							\
      initialize(C, M * N);																		\
      auto start_time_##name = std::chrono::high_resolution_clock::now(); 				    	\
      name(A, B, C, M, N, K);																	\
      auto end_time_##name = std::chrono::high_resolution_clock::now(); 						\
      time_##name += end_time_##name - start_time_##name;						    			\
    }																							\
  std::chrono::duration<double, std::milli> duration_##name = time_##name / float(NUM_RUNS); 	\
  std::cout << "Time taken for GEMM (CPU," << #name <<"): " << duration_ ## name.count() << "ms" << std::endl; 


// reference CPU implementation of the GEMM kernel
// note that this implementation is naive and will run for longer for larger
// graphs
void gemm_cpu_o0(float* A, float* B, float *C, int M, int N, int K) {
  for (int n = 0; n < N; n++) {
    for (int m = 0; m < M; m++) {
      for (int k = 0; k < K; k++) {
		C[m * N + n]  += A[m * K + k]  * B[k * N + n];
      }
    }
  }
}

// Your optimized implementations go here
// note that for o4 you don't have to change the code, but just the compiler flags. So, you can use o3's code for that part
void gemm_cpu_o1(float* A, float* B, float *C, int M, int N, int K) {
  for (int m = 0; m < M; m++) {
	for (int k = 0; k < K; k++) {
		for (int n = 0; n < N; n++) {
			C[m * N + n] += A[m * K + k] * B[k * N + n];
		}
	}
  }
}

void gemm_cpu_o2(float* A, float* B, float *C, int M, int N, int K) {
  for (int mT = 0; mT < M; mT += TILE_SIZE) {
	for (int kT = 0; kT < K; kT += TILE_SIZE) {
	  for (int nT = 0; nT < N; nT += TILE_SIZE) {
		for (int m = mT; m < std::min(mT + TILE_SIZE, M); m++) {
	      for (int k = kT; k < std::min(kT + TILE_SIZE, K); k++) {
			for (int n = nT; n < std::min(nT + TILE_SIZE, N); n++) {
			  C[m * N + n] += A[m * K + k] * B[k * N + n];
			}
		  }
	    }
	  }
    }
  }
}

void gemm_cpu_o3(float* A, float* B, float *C, int M, int N, int K) {
  #pragma omp parallel for
  for (int mT = 0; mT < M; mT += TILE_SIZE) {
	for (int kT = 0; kT < K; kT += TILE_SIZE) {
	  for (int nT = 0; nT < N; nT += TILE_SIZE) {
		for (int m = mT; m < std::min(mT + TILE_SIZE, M); m++) {
	      for (int k = kT; k < std::min(kT + TILE_SIZE, K); k++) {
			for (int n = nT; n < std::min(nT + TILE_SIZE, N); n++) {
			  C[m * N + n] += A[m * K + k] * B[k * N + n];
			}
		  }
		}
	  }
	}
  }
}

#pragma GCC push_options
#pragma GCC optimize ("-O3")

void gemm_cpu_o4(float* A, float* B, float *C, int M, int N, int K) {
    // Identical to o3 just with pragma above.
    gemm_cpu_o3(A, B, C, M, N, K);
}

#pragma GCC pop_options


int main(int argc, char* argv[]) {
	if (argc < 3) {
	  std::cout << "Usage: mp1 <M> <N> <K>" << std::endl;
	  return 1;
	}

	int M = atoi(argv[1]);
	int N = atoi(argv[2]);
	int K = atoi(argv[3]);

	float* A = new float[M * K]();
	float* B = new float[K * N]();
	float* C = new float[M * N]();

	fillRandom(A, M * K);
	fillRandom(B, K * N);

	// Check if the kernel results are correct
	// note that even if the correctness check fails all optimized kernels will run.
	// We are not exiting the program at failure at this point.
	// It is a good idea to add more correctness checks to your code.
	// We may (at discretion) verify that your code is correct.
	float* refC = new float[Ref::M * Ref::N]();
	auto ref = Ref();
	CHECK(gemm_cpu_o0)
	CHECK(gemm_cpu_o1)
	CHECK(gemm_cpu_o2)
	CHECK(gemm_cpu_o3)
	CHECK(gemm_cpu_o4)
	delete[] refC;
	
	TIME(gemm_cpu_o0)
	TIME(gemm_cpu_o1)
	TIME(gemm_cpu_o2)
	TIME(gemm_cpu_o3)
	TIME(gemm_cpu_o4)

	delete[] A;
	delete[] B;
	delete[] C;

	return 0;
}