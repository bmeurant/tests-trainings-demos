package io.bmeurant.java.datetime;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.Duration;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static java.lang.IO.println;

/**
 * Demonstrates the core functionalities of the Java 8+ Date and Time API (java.time).
 */
public class DateTimeApiDemo {

    void main() {
        // 1. Creating Local Dates and Times
        createLocalDates();

        // 2. Working with Time Zones
        workWithTimeZones();

        // 3. Calculating Differences (Duration and Period)
        calculateDifferences();

        // 4. Formatting and Parsing
        formatAndParse();
    }

    /**
     * Demonstrates the use of LocalDate, LocalTime, and LocalDateTime.
     */
    private void createLocalDates() {
        println("--- 1. Local Dates and Times ---");

        // Current date
        LocalDate today = LocalDate.now();
        println("Today's date (LocalDate): " + today); // e.g., 2025-10-30

        // Specific date (year, month, day)
        LocalDate specificDate = LocalDate.of(2024, 5, 15);
        println("Specific date: " + specificDate);

        // Current time
        LocalTime now = LocalTime.now();
        println("Current time (LocalTime): " + now.truncatedTo(ChronoUnit.SECONDS));

        // Specific time (hour, minute, second)
        LocalTime meetingTime = LocalTime.of(14, 30, 0);
        println("Meeting time: " + meetingTime);

        // Combining date and time
        LocalDateTime currentDateTime = LocalDateTime.now();
        println("Current date/time (LocalDateTime): " + currentDateTime.truncatedTo(ChronoUnit.SECONDS));

        // Manipulation: adding days, months, etc. (returns a *new* immutable object)
        LocalDateTime futureDateTime = currentDateTime
                .plusDays(10)
                .minusHours(2)
                .withYear(2026);
        println("Future date/time (manipulated): " + futureDateTime.truncatedTo(ChronoUnit.SECONDS));
    }

    /**
     * Demonstrates ZonedDateTime for handling time zones.
     */
    private void workWithTimeZones() {
        println("\n--- 2. Working with Time Zones (ZonedDateTime) ---");

        // Get the current ZonedDateTime for the system's default time zone
        ZonedDateTime currentZoned = ZonedDateTime.now();
        println("Current Zoned DateTime (System): " + currentZoned.truncatedTo(ChronoUnit.SECONDS));

        // Get ZonedDateTime for a specific time zone
        ZoneId tokyoZone = ZoneId.of("Asia/Tokyo");
        ZonedDateTime tokyoTime = currentZoned.withZoneSameInstant(tokyoZone);
        println("Time in Tokyo: " + tokyoTime.truncatedTo(ChronoUnit.SECONDS));

        // Create a specific time in a specific zone
        LocalDateTime local = LocalDateTime.of(2025, 11, 1, 9, 0); // Nov 1st, 9:00 AM
        ZonedDateTime parisTime = ZonedDateTime.of(local, ZoneId.of("Europe/Paris"));
        println("Paris time: " + parisTime);

        // Converting Paris time to New York time
        ZonedDateTime newYorkTime = parisTime.withZoneSameInstant(ZoneId.of("America/New_York"));
        println("New York time: " + newYorkTime);
    }

    /**
     * Demonstrates Duration (time-based) and Period (date-based) calculations.
     */
    private void calculateDifferences() {
        println("\n--- 3. Calculating Differences (Duration/Period) ---");

        // Duration (measures time between two points in *time* like LocalTime or Instant)
        LocalTime start = LocalTime.of(9, 0);
        LocalTime end = LocalTime.of(17, 30);
        Duration workDuration = Duration.between(start, end);
        println("Work Duration between " + start + " and " + end + ": " + workDuration.toHours() + " hours and " + (workDuration.toMinutes() % 60) + " minutes.");

        // Period (measures time between two *dates*)
        LocalDate startDate = LocalDate.of(2020, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 10, 30);
        Period timePassed = Period.between(startDate, endDate);
        println("Time passed since 2020-01-01: " + timePassed.getYears() + " years, " + timePassed.getMonths() + " months, " + timePassed.getDays() + " days.");
    }

    /**
     * Demonstrates formatting date/time objects into strings and parsing strings back into objects.
     */
    private void formatAndParse() {
        println("\n--- 4. Formatting and Parsing ---");

        LocalDateTime dateTime = LocalDateTime.now();

        // Standard ISO formatter
        println("Default ISO Format: " + dateTime);

        // Custom formatter
        DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String formattedString = dateTime.format(customFormatter);
        println("Custom Format ('dd/MM/yyyy HH:mm:ss'): " + formattedString);

        // Parsing a string back into a LocalDateTime object
        String dateString = "25/12/2024 10:00:00";
        try {
            LocalDateTime parsedDateTime = LocalDateTime.parse(dateString, customFormatter);
            println("Parsed LocalDateTime: " + parsedDateTime);
        } catch (Exception e) {
            System.err.println("Error parsing date: " + e.getMessage());
        }
    }
}
