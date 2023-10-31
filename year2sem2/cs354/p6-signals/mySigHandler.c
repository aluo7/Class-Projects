////////////////////////////////////////////////////////////////////////////////
// Main File:        mySigHandler.c
// This File:        mySigHandler.c
// Other Files:      sendsig.c, divison.c
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

#include <signal.h> // import statements
#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <unistd.h>
#include <time.h>
#include <string.h>

#define INTERVAL_SECONDS 4 // constant value for interval

int counter = 0; // global variable counting SIGUSR signals

/**
 * This function is a signal handler for the alarm sigal (SIGALRM). 
 * When the alarm signal is received, it prints the current process 
 * ID and current time, and then sets another alarm to trigger in 
 * INTERVAL_SECONDS seconds.
 * 
 * int signum - represents the signal number that caused the handler
 * to be called
*/
void handle_alarm(int signum) {
    time_t current_time;
    if (time(&current_time) == -1) {
        printf("Error retrieving time\n");
        exit(1);
    }

    char* time_string = ctime(&current_time);
    if (time_string == NULL) {
        printf("Error converting time into string\n");
        exit(1);
    }
    printf("PID: %d CURRENT TIME: %s", getpid(), time_string);
    alarm(INTERVAL_SECONDS);
}

/**
 * This function is a signal handler for the user signal (SIGUSR1).
 * When the signal is received, it increments the counter variable.
 * 
 * int signum - represents the signal number that caused the handler
 * to be called
*/
void handle_sigusr1(int signum) {
    printf("SIGUSR1 handled and counted!\n");
    counter += 1;
}

/**
 * This function is a signal handler for the SIGINT signal (ctrl+c).
 * When the signal is received, it prints a message indicating how
 * many times the SIGUSR1 signal was handled, and exits.
 * 
 * int signum - represents the signal number that caused the handler
 * to be called
*/
void handle_sigint(int signum) {
    printf("\nSIGINT handled.\n");
    printf("SIGUSR1 was handled %d times. Exiting now.\n", counter);
    exit(0);
}

/**
 * This is the main function of the program. It initializes and handles
 * three different error signals (ALARM, SIGUSR1, SIGINT).
*/
int main() {
    // sigaction structs representing signals
    struct sigaction sa_alarm, sa_sigusr1, sa_sigint;

    // zeroing out memory pointers
    memset(&sa_alarm, 0, sizeof(sa_alarm));
    memset(&sa_sigusr1, 0, sizeof(sa_sigusr1));
    memset(&sa_sigint, 0, sizeof(sa_sigint));

    // setting signal handlers
    sa_alarm.sa_handler = handle_alarm;
    sa_sigusr1.sa_handler = handle_sigusr1;
    sa_sigint.sa_handler = handle_sigint;
    
    // error checking
    if (sigaction(SIGALRM, &sa_alarm, NULL) != 0) {
        printf("Error Binding SIGALRM handler\n");
        exit(1);
    }

    if (sigaction(SIGUSR1, &sa_sigusr1, NULL) != 0) {
        printf("Error Binding SIGUSR1 handler\n");
        exit(1);
    }

    if (sigaction(SIGINT, &sa_sigint, NULL) != 0) {
        printf("Error Binding SIGINT handler\n");
        exit(1);
    }

    
    printf("PID and time print every %d seconds.\n", INTERVAL_SECONDS);
    printf("Type Ctrl-C to end the program.\n");

    // alarm called every INTERVAL_SECONDS
    alarm(INTERVAL_SECONDS);

    while (1) {
        // infinite loop
    }

    return 0;
}