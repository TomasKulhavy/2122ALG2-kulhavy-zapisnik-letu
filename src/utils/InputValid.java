package utils;

import org.w3c.dom.ranges.RangeException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

/**
 * Pomocná třída na ošetření výjimek a vstupů
 * @author Tomáš Kulhavý
 */
public class InputValid {
    public InputValid() {
    }

    /**
     * Parsuje číslo
     * @param input vstup String
     * @return Boolean
     */
    public static boolean parseToNumber(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }

    public static boolean parseTimeInput(String input) {
        try {
            LocalDateTime.parse(input);
            return true;
        } catch(DateTimeParseException e) {
            return false;
        }
    }

    public static boolean parseDateInput(String input) {
        try {
            LocalDate.parse(input);
            return true;
        } catch(DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Vrací exception
     * @param input
     * @param max
     * @throws RangeException
     */
    public static void parseRange(int input, int max) throws RangeException {
        if(input >= max) throw new RangeException((short)input, "Jste mimo rozsah hodnot");
    }

    /**
     * Parsuje rozmezí int hodnot
     * @param max Maximální hodnota
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
     * Metoda, která námm parsne čas ze Stringu
     * @param timeInput Čas ve Stringu
     * @param dateCal Datum ve formátu LocalDate
     * @return Čas ve formátu LocalDateTime
     */
    public static LocalDateTime parseTime(String timeInput, LocalDate dateCal) {
        String[] timeArr = timeInput.split(":");
        return dateCal.atTime(Integer.parseInt(timeArr[0]), Integer.parseInt(timeArr[1]));
    }

    /**
     * Metoda, která námm parsne datum ze Stringu
     * @param dateInput Datum ve Stringu
     * @return Datum ve formátu LocalDate
     */
    public static LocalDate parseDate(String dateInput) {
        String[] dateArr = dateInput.split("\\.");
        return LocalDate.of(Integer.parseInt(dateArr[2]), Integer.parseInt(dateArr[1]), Integer.parseInt(dateArr[0]));
    }
}
