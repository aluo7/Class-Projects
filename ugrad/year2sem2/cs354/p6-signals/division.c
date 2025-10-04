////////////////////////////////////////////////////////////////////////////////
// Main File:        division.c
// This File:        division.c
// Other Files:      mySigHandler.c, sendsig.c
// Semester:         CS 354 Lecture 002 Spring 2023
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
//////////////////////////// 80 columns wide ///////////////////////////////////

#include <stdio.h> // import statements
#include <stdlib.h>
#include <string.h>
#include <signal.h>

int count = 0; // global variable counting number of successful division 
               // operations

/**
 * This function is a signal handler for the SIGFPE signal. 
 * When the alarm signal is received, it prints an error message as well 
 * as the number of operations completed, and then terminates the program.
 * 
 * int signum - represents the signal number that caused the handler
 * to be called
*/
void handle_sigfpe(int sig) {
    printf("Error: a division by 0 operation was attempted.\n");
    printf("Total number of operations completed successfully: %d\n", count);
    printf("The program will be terminated.\n");
    exit(0);
}

/**
 * This function is a signal handler for the SIGINT signal (ctrl+c).
 * When the signal is received, it prints a message indicating how
 * many successful operations were completed, and exits.
 * 
 * int signum - represents the signal number that caused the handler
 * to be called
*/
void handle_sigint(int sig) {
    printf("\nTotal number of operations completed successfully: %d\n", count);
    printf("The program will be terminated.\n");
    exit(0);

}

int main() {
    char buf[100]; // buffer for fgets
    int int1, int2;

    struct sigaction sa_sigfpe, sa_sigint; // sigaction structs representing signals
    
    // zeroing out memory pointers
    memset(&sa_sigfpe, 0, sizeof(sa_sigfpe));
    memset(&sa_sigint, 0, sizeof(sa_sigint));

    // setting signal handlers
    sa_sigfpe.sa_handler = handle_sigfpe;
    sa_sigint.sa_handler = handle_sigint;

    // error checking
    if (sigaction(SIGFPE, &sa_sigfpe, NULL) != 0) {
        printf("Error Binding SIGFPE handler\n");
        exit(1);
    }

    if (sigaction(SIGINT, &sa_sigint, NULL) != 0) {
        printf("Error Binding SIGINT handler\n");
        exit(1);
    }

    while (1) {
        printf("Enter first integer: "); // asks for user input
        if (fgets(buf, sizeof(buf), stdin) == NULL) { // error checking
            printf("Error reading input\n");
            exit(1);
        }
        int1 = atoi(buf);

        printf("Enter second integer: "); // asks for user input
        if (fgets(buf, sizeof(buf), stdin) == NULL) { // error checking
            perror("Error reading input");
            exit(1);
        }
        int2 = atoi(buf);

        int quotient = int1 / int2; // calculating quotient
        int remainder = int1 % int2; // calculating remainder

        printf("%d / %d is %d with a remainder of %d\n", int1, int2, quotient, remainder);

        count++;
    }

    return 0;
}