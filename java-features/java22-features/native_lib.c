// native_lib.c
#include <stdio.h>
#include <string.h> // For strlen

// A simple C function to concatenate two strings
int concatenate_strings(char* dest, const char* str1, const char* str2, int dest_size) {
    int len1 = strlen(str1);
    int len2 = strlen(str2);

    if (len1 + len2 + 1 > dest_size) { // +1 for null terminator
        return -1; // Buffer too small
    }

    strcpy(dest, str1);
    strcat(dest, str2);
    return len1 + len2; // Return total length
}

// A simple C function to print a string
void print_from_c(const char* message) {
    printf("Message from C: %s\n", message);
}