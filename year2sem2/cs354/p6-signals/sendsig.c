////////////////////////////////////////////////////////////////////////////////
// Main File:        sendsig.c
// This File:        sendsig.c
// Other Files:      mySigHandler.c, division.c
// Semester:         CS 354 Lecture 002 Spring 2023
// Instructor:       deppeler
// 
// Author:           (your name)
// Email:            (your wisc email address)
// CS Login:         (your CS login name)
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
#include <signal.h>
#include <string.h>

/**
 * This program takes two CLA's (signal type and PID). Determines
 * signal type and calls kill() function on the given PID.
 * 
 * int argc - number of arguments
 * char *argv[] - CLA's.
*/
int main(int argc, char *argv[]) {
    if (argc != 3) { // checking that sendsig is called correctly
        printf("Usage: sendsig <signal type> <pid>\n");
        exit(1);
    }

    int pid = atoi(argv[2]); // converts from str to int
    if (pid <= 0) { // error checking for valid PID
        printf("Invalid PID\n");
        exit(1);
    }

    int signal_type; // variable representing the signal type
    if (strcmp(argv[1], "-u") == 0) { // if SIGUSR1 signal
        signal_type = SIGUSR1;
    } else if (strcmp(argv[1], "-i") == 0) { // if SIGINT signal
        signal_type = SIGINT;
    } else { // invalid signal
        printf("Invalid signal type]n");
        exit(1);
    }

    if (kill(pid, signal_type) != 0) { // error checking
        printf("Error sending signal\n");
        exit(1);
    }

    return 0;
}
