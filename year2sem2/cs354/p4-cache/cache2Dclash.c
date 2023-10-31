////////////////////////////////////////////////////////////////////////////////
// Main File:        cache2Dclash.c
// This File:        cache2Dclash.c
// Other Files:      cache1D.c, cache2Drows.c, cache2Dcols.c
// Semester:         CS 354 Lecture 02 Spring 2023
// Instructor:       deppeler
// 
// Author:           Alan Luo
// Email:            aluo7@wisc.edu
// CS Login:         aluo
//
/////////////////////////// OTHER SOURCES OF HELP //////////////////////////////
//                   fully acknowledge and credit all sources of help,
//                   other than Instructors and TAs.
//
// Persons:          Identify persons by name, relationship to you, and email.
//                   Describe in detail the the ideas and help they provided.
//
// Online sources:   avoid web searches to solve your problems, but if you do
//                   search, be sure to include Web URLs and description of 
//                   of any information you find.
///////////////////////////////////////////////////////////////////////////////

#include <stdio.h>

#define ARR_ROWS 128
#define ARR_COLS 8
#define ITERATION 100
int arr2D[ARR_ROWS][ARR_COLS];

int main() {
    for (int i = 0; i < ITERATION; i++) {
        for (int j = 0; j < ARR_ROWS; j += 64) {
            for (int k = 0; k < ARR_COLS; k++) {
                arr2D[j][k] = i + j + k;
            }
        }
    }

    return 0;
}
