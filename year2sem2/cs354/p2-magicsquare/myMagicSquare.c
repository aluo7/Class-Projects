///////////////////////////////////////////////////////////////////////////////
// Copyright 2020 Jim Skrentny
// Posting or sharing this file is prohibited, including any changes/additions.
// Used by permission, CS 354 Spring 2022, Deb Deppeler
////////////////////////////////////////////////////////////////////////////////
   
///////////////////////////////////////////////////////////////////////////////
//
// Copyright 2021 Deb Deppeler
// Posting or sharing this file is prohibited, including any changes/additions.
//
// We have provided comments and structure for this program to help you get 
// started.  Later programs will not provide the same level of commenting,
// rather you will be expected to add same level of comments to your work.
// 09/20/2021 Revised to free memory allocated in get_board_size function.
// 01/24/2022 Revised to use pointers for CLAs
//
////////////////////////////////////////////////////////////////////////////////
// Main File:        check_board.c
// This File:        check_board.c
// Other Files:      n/a
// Semester:         CS 354 Lec 002 Spring 2023
// Instructor:       Deppeler
//
// Author:           Alan Luo
// Email:            aluo7@wisc.edu
// CS Login:         aluo
// GG#:              4
//
/////////////////////////// OTHER SOURCES OF HELP //////////////////////////////
//                   Fully acknowledge and credit all sources of help,
//                   including family, friencs, classmates, tutors,
//                   Peer Mentors, TAs, and Instructor.
//
// Persons:          Identify persons by name, relationship to you, and email.
//                   Describe in detail the the ideas and help they provided.
//
// Online sources:   Avoid web searches to solve your problems, but if you do
//                   search, be sure to include Web URLs and description of
//                   of any information you find.
////////////////////////////////////////////////////////////////////////////////

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// Structure that represents a magic square
typedef struct {
    int size;           // dimension of the square
    int **magic_square; // pointer to heap allocated magic square
} MagicSquare;

/* 
 * Prompts the user for the magic square's size, reads it,
 * checks if it's an odd number >= 3 (if not display the required
 * error message and exit), and returns the valid number.
 */
int getSize() {
	int size;

	// prompt the user
	printf("Enter magic square's size (odd integer >= 3)\n");
	scanf("%d", &size);

	// arg requirements
	if (size % 2 == 0) {
		printf("Magic square size must be odd.\n");
		exit(1);
	} else if (size < 3) {
		printf("Magic square size must be >= 3.\n");
		exit(1);
	}

    return size;   
} 
   
/* 
 * Makes a magic square of size n using Siamese magic square 
 * algorithm from assignment and returns a pointer to the 
 * completed MagicSquare struct.
 *
 * n the number of rows and columns
 */
MagicSquare *generateMagicSquare(int n) {
	// instantiate MagicSquare struct
	MagicSquare *square = calloc(1, sizeof(MagicSquare)); 

	// checking that space was allocated properly
	if (square == NULL) {
		printf("Ran out of memory\n");
		exit(1);
	}
	
	square -> size = n;
	
	// allocating space for 2D array
	square -> magic_square = calloc(n, sizeof(int *));
	if (square -> magic_square == NULL) {
		printf("Ran out of memory\n");
		exit(1);
	}

	// checking that space was allocated properly
	for (int i = 0; i < n; i++) {
		*(square -> magic_square + i) = calloc(n, sizeof(int));
		if (*(square -> magic_square + i) == NULL) {
			printf("Ran out of memory\n");
			exit(1);
		}
	}
	
	// setting starting position
	int row = 0;
	int col = n / 2;
	int num = 1;

	// fills in magic square
	while (num <= n * n) {
		// place num at current position
		*(*(square -> magic_square + row) + col) = num;

		// calculating next position
		int nextRow = (row - 1 + n) % n;
		int nextCol = (col + 1) % n;

		// if space already filled -> move down
		if (*(*(square -> magic_square + nextRow) + nextCol) != 0) {
			nextRow = (row + 1) % n;
			nextCol = col;
		}

		// update indexing
		row = nextRow;
		col = nextCol;
		num++;
	}

	return square;
} 

/* 
 * Opens a new file (or overwrites the existing file)
 * and writes the square in the specified format.
 *
 * magic_square the magic square to write to a file
 * filename the name of the output file
 */
void fileOutputMagicSquare(MagicSquare *magic_square, char *filename) {
	// opens the file for writing
	FILE *fp = fopen(filename, "w");

	// checks that the file was opened properly
	if (fp == NULL) {
		printf("Can't open file for reading.\n");
		exit(1);
	}

	// writing to the file
	fprintf(fp, "%d", magic_square -> size);
    for (int i = 0; i < magic_square -> size; i++) {
		fprintf(fp, "\n");
        for (int j = 0; j < magic_square -> size; j++) {
            if (magic_square -> size - j > 1) {
                fprintf(fp, "%d,", *(*(magic_square->magic_square + i) + j));
            } else {
                fprintf(fp, "%d", *(*(magic_square->magic_square + i) + j));
            }
        }
    }
	fprintf(fp, "\n");
	
	// closes file and checks that it was closed properly
	if (fclose(fp) != 0) {
		printf("Error while closing the file.\n");
		exit(1);
	} 
}

/* 
 * Generates a magic square of the user specified size and
 * output the quare to the output filename
 * 
 * The first CLA = executable file, second CLA = output filename
 */
int main(int argc, char **argv) {
    // Check input arguments to get output filename
	if (argc != 2) {
		printf("Usage: ./myMagicSquare <output_filename>\n");
		exit(1);
	}

    // Get magic square's size from user
	int size = getSize();

    // Generate the magic square
	MagicSquare *square = generateMagicSquare(size);

    // Output the magic square
	fileOutputMagicSquare(square, *(argv + 1));
	
	// free memory
	for (int i = 0; i < size; i++) { 
		free(*(square -> magic_square + i));
	}
	free(square -> magic_square);
	free(square);

	return 0;
}
