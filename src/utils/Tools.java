package utils;

import app.Flight;
import app.FlightDiary;
import app.Plane;
import app.TypeOfLicence;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * Pomocná třída na vedlejší metody
 *
 * @author Tomáš Kulhavý
 */
public class Tools {

    private static List<Plane> planes = new ArrayList<>();

    public Tools() {
    }

    /**
     * Metoda, která vypíše letadla do konzole
     *
     * @param planeList List letadel
     */
    public static void printPlanes(List<Plane> planeList) {
        for (int i = 0; i < planeList.size(); i++) {
            System.out.println(i + 1 + "." + " - " + planeList.get(i).getRegistration());
        }
    }

    /**
     * Metoda, která vypíše lety ze zápisníku pilota v závisloti na typu letadla
     *
     * @param flights  List letů
     * @param isGlider Je toto větroň?
     */
    public static void printFlight(List<Flight> flights, boolean isGlider) {
        if (isGlider) {
            System.out.format("%-15s%-15s%-15s%-20s%-20s%-20s%-20s%-15s%-15s%-15s%-15s%n", "Datum letu", "Cas odletu", "Cas priletu", "Letiste odletu", "Letiste priletu", "Zpusob vzletu", "Cas letu", "Pocet startu", "Letadlo", "Registrace", "Poznamka");
            for (Flight flight : flights) {
                Plane plane = flight.getPlane();
                System.out.format("%-15s%-15s%-15s%-20s%-20s%-20s%-20s%-15s%-15s%-15s%-15s%n", flight.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), flight.getTakeoffTime().getHour() + ":" + flight.getTakeoffTime().getMinute(), flight.getLandingTime().getHour() + ":" + flight.getLandingTime().getMinute(), flight.getTakeoff(), flight.getLanding(), flight.getTypeOfTakeOff(), getTotalTime(flight.getFlightTimeMinutes()), flight.getTakeoffNo(), plane.getName(), plane.getRegistration(), flight.getTypeOfFlight());
            }
            System.out.println();
        } else {
            System.out.format("%-15s%-15s%-15s%-20s%-20s%-20s%-15s%-15s%-15s%-15s%n", "Datum letu", "Cas odletu", "Cas priletu", "Letiste odletu", "Letiste priletu", "Cas letu", "Pocet startu", "Letadlo", "Registrace", "Poznamka");
            for (Flight flight : flights) {
                Plane plane = flight.getPlane();
                System.out.format("%-15s%-15s%-15s%-20s%-20s%-20s%-15s%-15s%-15s%-15s%n", flight.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), flight.getTakeoffTime().getHour() + ":" + flight.getTakeoffTime().getMinute(), flight.getLandingTime().getHour() + ":" + flight.getLandingTime().getMinute(), flight.getTakeoff(), flight.getLanding(), getTotalTime(flight.getFlightTimeMinutes()), flight.getTakeoffNo(), plane.getName(), plane.getRegistration(), flight.getTypeOfFlight());
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
    public static void printPlaneFlight(List<Flight> flights, boolean isGlider) {
        if (isGlider) {
            System.out.format("%-20s%-20s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%n", "Letiste odletu", "Letiste priletu", "Datum letu", "Cas odletu", "Cas priletu", "Cas letu", "Pocet startu", "Zpusob vzletu", "Poznamka", "Pilot");
            for (Flight flight : flights) {
                System.out.format("%-20s%-20s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%n", flight.getTakeoff(), flight.getLanding(), flight.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), flight.getTakeoffTime().getHour() + ":" + flight.getTakeoffTime().getMinute(), flight.getLandingTime().getHour() + ":" + flight.getLandingTime().getMinute(), getTotalTime(flight.getFlightTimeMinutes()), flight.getTakeoffNo(), flight.getTypeOfTakeOff(), flight.getTypeOfFlight(), flight.getPilot());
            }
            System.out.println();
        } else {
            System.out.format("%-20s%-20s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%n", "Letiste odletu", "Letiste priletu", "Datum letu", "Cas odletu", "Cas priletu", "Cas letu", "Pocet startu", "Poznamka", "Pilot");
            for (Flight flight : flights) {
                System.out.format("%-20s%-20s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%n", flight.getTakeoff(), flight.getLanding(), flight.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), flight.getTakeoffTime().getHour() + ":" + flight.getTakeoffTime().getMinute(), flight.getLandingTime().getHour() + ":" + flight.getLandingTime().getMinute(), getTotalTime(flight.getFlightTimeMinutes()), flight.getTakeoffNo(), flight.getTypeOfFlight(), flight.getPilot());
            }
            System.out.println();
        }
    }

    /**
     * Metoda, která nám vrátí minuty na hodiny
     *
     * @param minutesInput Celkové minuty
     * @return Hodiny ve formátu HH:MM
     */
    public static String getTotalTime(int minutesInput) {
        int hours = minutesInput / 60;
        int minutes = minutesInput % 60;
        return String.format("%d:%02d", hours, minutes);
    }

    /**
     * Metoda, která nám vygeneruje typy licencí pro volbu v UI
     */
    public static void generateTypesOfLicence() {
        for (int i = 0; i < TypeOfLicence.values().length; i++) {
            System.out.println(i + 1 + "." + " - " + TypeOfLicence.valueOf(i));
        }
    }

    /**
     * Metoda, která nám vypíše licence pilota
     *
     * @param diaries List licencí pilota
     */
    public static void printLicences(List<FlightDiary> diaries) {
        for (int i = 0; i < diaries.size(); i++) {
            System.out.println(i + 1 + "." + " - " + diaries.get(i).getType());
        }
    }

    /**
     * Metoda, která v souboru přepíše hodnotu novou hodnotou
     *
     * @param fileInput   Soubor pro změnu
     * @param replaceOld  Starý text
     * @param replaceWith Nový text
     */
    public static void replaceSelected(File fileInput, String replaceOld, String replaceWith) {
        try {
            // input the file content to the StringBuffer "input"
            BufferedReader file = new BufferedReader(new FileReader(fileInput));
            StringBuilder inputBuffer = new StringBuilder();
            String line;

            while ((line = file.readLine()) != null) {
                inputBuffer.append(line);
                inputBuffer.append('\n');
            }
            file.close();
            String inputStr = inputBuffer.toString();

            // logic to replace lines in the string (could use regex here to be generic)
            inputStr = inputStr.replace(replaceOld, replaceWith);

            // write the new string with the replaced line OVER the same file
            FileOutputStream fileOut = new FileOutputStream(fileInput);
            fileOut.write(inputStr.getBytes());
            fileOut.close();

        } catch (Exception e) {
            System.out.println("Problem reading file.");
        }
    }

    /**
     * Metoda, která nám zobrazí hlavnáí menu v UI
     */
    public static void showMenu() {
        String menu = """
                1. Pridat typ zapisniku letu
                2. Pridat let
                3. Pridat letadlo
                4. Zobrazit zapisnik
                5. Zobrazit zapisnik letadla
                6. Zapsat letadla do binarniho souboru
                7. Vypsat letadla z binarniho souboru
                0. Odhlasit se""";
        System.out.println(menu);
    }

    /**
     * Metoda, která nám vrátí menu pro výběr řazení listu
     */
    public static void showSortMenu() {
        String menu = """
                1. Seradit sestupne podle datumu
                2. Seradit vzestupne podle datumu
                3. Exportovat do PDF
                0. Zpet""";
        System.out.println(menu);
    }

    /**
     * Metoda, která nám zjistí, jestli je toto větroň
     *
     * @param licence Typ licence
     * @return True : je, False : není
     */
    public static boolean isGlider(TypeOfLicence licence) {
        return licence == TypeOfLicence.SPL;
    }

    /**
     * Metoda, která přidá letadlo
     *
     * @param plane Objekt letadla
     */
    public static void addPlane(Plane plane) {
        planes.add(plane);
    }

    /**
     * Vrátí list letadel
     *
     * @return List letadel
     */
    public static List<Plane> getPlanes() {
        return planes;
    }

    /**
     * Metoda, která načte všechna letadla a jejich hodnoty ze souboru
     *
     * @return List letadel s jejich hodnotami ze souborů
     * @throws FileNotFoundException Soubor nenalezen
     */
    public static List<Plane> loadAllPlanes() throws FileNotFoundException {
        planes.removeAll(planes);
        File dir = new File("data/exported-data/");
        File[] files = dir.listFiles((dir1, name) -> name.endsWith(".plane"));

        for (File xmlfile : Objects.requireNonNull(files)) {
            File file = new File("data/exported-data/" + xmlfile.getName());
            Scanner sc = new Scanner(file);
            String data = sc.nextLine();
            String[] list = data.split(", ");
            String name = list[0];
            TypeOfLicence licence = TypeOfLicence.findByLicence(list[1]);
            String registration = list[2];
            Plane plane = new Plane(name, licence, registration, true);
            addPlane(plane);
        }
        return getPlanes();
    }

    /**
     * Zápis do binárního souboru
     *
     * @throws IOException
     */
    public static void saveToBinaryFile() throws IOException {
        File file = new File("data/bin-data/planesInBinary.bin");
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(file))) {
            out.writeInt(planes.size());
            for (Plane plane : planes) {
                out.writeUTF(plane.getRegistration());
            }
        }
    }

    /**
     * Výpis z binárního souboru
     *
     * @return
     * @throws IOException
     */
    public static String readFromBinaryResults() throws IOException {
        File file = new File("data/bin-data/planesInBinary.bin");
        StringBuilder sb = new StringBuilder();
        int planesNo;
        String reg = "";
        int no = 1;
        try (DataInputStream in = new DataInputStream(new FileInputStream(file))) {
            boolean end = false;
            while (!end) {
                try {
                    no = 1;
                    planesNo = in.readInt();
                    for (int i = 0; i < planesNo; i++) {
                        reg = in.readUTF();
                        sb.append(String.format("%2s %10s", no, reg));
                        sb.append("\n");
                        no++;
                    }
                } catch (EOFException e) {
                    end = true;
                }
            }
        }
        return sb.toString();
    }
}