package utils;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Interface k InputValid
 */
public interface IInputValid {
    boolean parseToNumber(String input);

    boolean parseRangeInput(int max, int input);

    boolean parseTime(String timeInput, LocalDate dateCal);

    LocalDateTime parseTimeInput(String timeInput, LocalDate dateCal);

    LocalDate parseDate(int day, int month, int year);

    boolean parseDayInMonth(int day, int month, int year);

    boolean parseMonth(int month);

    boolean parseDay(int day, int month, int year);
}