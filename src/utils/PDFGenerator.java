package utils;

import app.Flight;
import app.Pilot;
import app.Plane;
import app.TypeOfLicence;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PDFGenerator {
    static Document document = new Document();

    /**
     * Metoda, která vypíše lety ze zápisníku pilota v závisloti na typu letadla
     *
     * @param flights  List letů
     * @param isGlider Je toto větroň?
     */
    public static void printFlight(List<Flight> flights, boolean isGlider) throws DocumentException {
        if (isGlider) {
            document.add(new Paragraph(String.format("%-15s%-15s%-15s%-20s%-20s%-20s%-20s%-15s%-15s%-15s%-15s%n", "Datum letu", "Cas odletu", "Cas priletu", "Letiste odletu", "Letiste priletu", "Zpusob vzletu", "Cas letu", "Pocet startu", "Letadlo", "Registrace", "Poznamka")));
            for (Flight flight : flights) {
                Plane plane = flight.getPlane();
                document.add(new Paragraph(String.format("%-15s%-15s%-15s%-20s%-20s%-20s%-20s%-15s%-15s%-15s%-15s%n", flight.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), flight.getTakeoffTime().getHour() + ":" + flight.getTakeoffTime().getMinute(), flight.getLandingTime().getHour() + ":" + flight.getLandingTime().getMinute(), flight.getTakeoff(), flight.getLanding(), flight.getTypeOfTakeOff(), Tools.getTotalTime(flight.getFlightTimeMinutes()), flight.getTakeoffNo(), plane.getName(), plane.getRegistration(), flight.getTypeOfFlight())));
            }
            System.out.println();
        } else {
            document.add(new Paragraph(String.format("%-15s%-15s%-15s%-20s%-20s%-20s%-15s%-15s%-15s%-15s%n", "Datum letu", "Cas odletu", "Cas priletu", "Letiste odletu", "Letiste priletu", "Cas letu", "Pocet startu", "Letadlo", "Registrace", "Poznamka")));
            for (Flight flight : flights) {
                Plane plane = flight.getPlane();
                document.add(new Paragraph(String.format("%-15s%-15s%-15s%-20s%-20s%-20s%-15s%-15s%-15s%-15s%n", flight.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), flight.getTakeoffTime().getHour() + ":" + flight.getTakeoffTime().getMinute(), flight.getLandingTime().getHour() + ":" + flight.getLandingTime().getMinute(), flight.getTakeoff(), flight.getLanding(), Tools.getTotalTime(flight.getFlightTimeMinutes()), flight.getTakeoffNo(), plane.getName(), plane.getRegistration(), flight.getTypeOfFlight())));
            }
            System.out.println();
        }
    }

    /**
     * Metoda, která vypíše lety ze zápisníku letadla v závisloti na typu letadla
     *
     * @param flights  List letů
     * @param isGlider Je toto větroň?
     */
    public static void printPlaneFlight(List<Flight> flights, boolean isGlider) throws DocumentException {
        if (isGlider) {
            document.add(new Paragraph(String.format("%-20s%-20s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%n", "Letiste odletu", "Letiste priletu", "Datum letu", "Cas odletu", "Cas priletu", "Cas letu", "Pocet startu", "Zpusob vzletu", "Poznamka", "Pilot")));
            for (Flight flight : flights) {
                document.add(new Paragraph(String.format("%-20s%-20s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%n", flight.getTakeoff(), flight.getLanding(), flight.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), flight.getTakeoffTime().getHour() + ":" + flight.getTakeoffTime().getMinute(), flight.getLandingTime().getHour() + ":" + flight.getLandingTime().getMinute(), Tools.getTotalTime(flight.getFlightTimeMinutes()), flight.getTakeoffNo(), flight.getTypeOfTakeOff(), flight.getTypeOfFlight(), flight.getPilot())));
            }
            System.out.println();
        } else {
            document.add(new Paragraph(String.format("%-20s%-20s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%n", "Letiste odletu", "Letiste priletu", "Datum letu", "Cas odletu", "Cas priletu", "Cas letu", "Pocet startu", "Poznamka", "Pilot")));
            for (Flight flight : flights) {
                document.add(new Paragraph(String.format("%-20s%-20s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%n", flight.getTakeoff(), flight.getLanding(), flight.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), flight.getTakeoffTime().getHour() + ":" + flight.getTakeoffTime().getMinute(), flight.getLandingTime().getHour() + ":" + flight.getLandingTime().getMinute(), Tools.getTotalTime(flight.getFlightTimeMinutes()), flight.getTakeoffNo(), flight.getTypeOfFlight(), flight.getPilot())));
            }
            System.out.println();
        }
    }

    /**
     * Metoda exportuje lety pilota do PDF souboru
     *
     * @param flights       List s lety
     * @param pilot         Pilot
     * @param typeOfLicence Licence
     * @param isGlider      Je to větroň?
     * @return boolean
     */
    public static void saveToPdf(List<Flight> flights, Pilot pilot, TypeOfLicence typeOfLicence, boolean isGlider) throws FileNotFoundException, DocumentException {
        PdfWriter.getInstance(document, new FileOutputStream("data//pdf-export//" + pilot.getName() + "_" + typeOfLicence + ".pdf"));
        document.setPageSize(PageSize.A3.rotate());
        document.open();
        printFlight(flights, isGlider);
        document.close();

    }

    /**
     * Metoda exportuje lety letadla do PDF souboru
     *
     * @param flights  List s lety
     * @param plane    Letadlo
     * @param isGlider Je to větroň?
     * @return boolean
     */
    public static void saveToPdfPlane(List<Flight> flights, Plane plane, boolean isGlider) throws FileNotFoundException, DocumentException {
        PdfWriter.getInstance(document, new FileOutputStream("data//pdf-export//" + plane.getRegistration() + ".pdf"));
        document.setPageSize(PageSize.A3.rotate());
        document.open();
        printPlaneFlight(flights, isGlider);
        document.close();
    }
}