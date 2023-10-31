////////////////////////////////////////////////////////////////////////////////
// Main File:        cache1D.c
// This File:        cache1D.c
// Other Files:      cache2Drows.c, cache2Dcols.c, cache2Dclash.c
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

#define ARR_SIZE 100000
int arr[ARR_SIZE];

int main() {
    for (int i = 0; i < ARR_SIZE; i++) {
        arr[i] = i;
    }
}
