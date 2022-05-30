package utils;

import org.w3c.dom.ranges.RangeException;
import ui.Main;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Pomocná třída na ošetření výjimek a vstupů
 *
 * @author Tomáš Kulhavý
 */
public class InputValid extends Main {
    public InputValid() {
    }

    /**
     * Parsuje číslo
     *
     * @param input vstup String
     * @return Boolean
     */
    public static boolean parseToNumber(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Vrací exception
     *
     * @param input Int vstup
     * @param max Maximální číslo
     * @throws RangeException Mimo rozsah
     */
    public static void parseRange(int input, int max) throws RangeException {
        if (input > max) throw new RangeException((short) input, "Jste mimo rozsah hodnot");
    }

    /**
     * Parsuje rozmezí int hodnot
     *
     * @param max   Maximální hodnota
     * @param input Vstup
     * @return Boolean
     */
    public static boolean parseRangeInput(int max, int input) {
        try {
            parseRange(input, max);
            return true;
        } catch (RangeException e) {
            return false;
        }
    }

    /**
     * Metoda, která nám parsne čas ze Stringu
     *
     * @param timeInput Čas ve Stringu
     * @param dateCal   Datum ve formátu LocalDate
     * @return Čas ve formátu LocalDateTime
     */
    public static boolean parseTime(String timeInput, LocalDate dateCal) {
        String[] timeArr = timeInput.split(":");
        try {
            dateCal.atTime(Integer.parseInt(timeArr[0]), Integer.parseInt(timeArr[1]));
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }

    /**
     * Parsuje zadaný čas ve Stringu.
     *
     * @param timeInput Zadaný čas ve Stringu
     * @param dateCal   Zadané datum
     * @return Parsovaný čas na LocalDateTime
     */
    public static LocalDateTime parseTimeInput(String timeInput, LocalDate dateCal) {
        String[] timeArr = timeInput.split(":");
        parseTime(timeInput, dateCal);
        return dateCal.atTime(Integer.parseInt(timeArr[0]), Integer.parseInt(timeArr[1]));
    }

    /**
     * Metoda, která námm parsne datum ze Stringu
     *
     * @param day   Den letu
     * @param month Mesic letu
     * @param year  Rok letu
     * @return Datum ve formátu LocalDate
     */
    public static LocalDate parseDate(int day, int month, int year) {
        return LocalDate.of(year, month, day);
    }

    /**
     * Parsuje Den v měsíci
     *
     * @param day   Den
     * @param month Měsíc
     * @param year  Rok
     * @return Vrátí boolean, jestli je den správně
     */
    public static boolean parseDayInMonth(int day, int month, int year) {
        boolean result;
        if (month == 2) {
            boolean leapYear = year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
            if (leapYear) {
                result = parseRangeInput(29, day);
            } else {
                result = parseRangeInput(28, day);
            }
        } else if (month % 2 == 1) {
            result = parseRangeInput(31, day);
        } else {
            result = parseRangeInput(30, day);
        }

        return result;
    }

    /**
     * Parsne jestli je měsíc ve správném rozsahu
     *
     * @param month Měsíc v roce
     * @return boolean
     */
    public static boolean parseMonth(int month) {
        try {
            return parseRangeInput(12, month);
        } catch (RangeException e) {
            return false;
        }
    }

    /**
     * Parsne den v měsící/roce
     *
     * @param day   Den v měsící
     * @param month Měsíc v roce
     * @param year  Rok
     * @return boolean
     */
    public static boolean parseDay(int day, int month, int year) {
        try {
            return parseDayInMonth(day, month, year);
        } catch (RangeException e) {
            return false;
        }
    }
}
