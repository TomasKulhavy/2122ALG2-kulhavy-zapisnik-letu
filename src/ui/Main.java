package ui;

import app.FlightDiary;
import app.Pilot;
import app.Plane;
import app.TypeOfLicence;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.SQLOutput;
import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        boolean end = false;
        boolean login = false;
        Pilot pilot = null;
        Plane plane = null;
        do {
            System.out.println("---- Vitejte v zapisniku svych letu ----");
            System.out.println("Zadejte sve jmeno [bez diakritiky] - pro vypnuti aplikace stiskni [0]: ");
            String temp = sc.nextLine();
            if (temp.equals("0")) {
                end = true;
                break;
            }
            String[] tempData = temp.split(" ");

            File logbook = new File(tempData[0] + "_" + tempData[1] + ".txt");
            boolean exist = logbook.exists();
            if (!exist) {
                pilot = new Pilot(tempData[0], tempData[1]);
            } else {
                pilot = new Pilot(tempData[0], tempData[1], logbook);
            }
            login = true;

            do {
                String menu = """
                        1. Pridat typ zapisniku letu
                        2. Pridat let
                        3. Pridat letadlo
                        4. Zobrazit souhrne soucty podle typu licence
                        5. Zobrazit zapisnik
                        0. Odhlasit se""";
                System.out.println(menu);
                int tempLogin = sc.nextInt();
                if (tempLogin == 0) {
                    login = false;
                    sc.nextLine();
                    break;
                } else if (tempLogin == 1) {
                    System.out.println("Zadej pozadovany typ licence ze seznamu: ");
                    generateTypesOfLicence();
                    int tempLicence = sc.nextInt() - 1;
                    FlightDiary diary = new FlightDiary(pilot, TypeOfLicence.valueOf(tempLicence));
                    pilot.addDiary(diary);
                } else if (tempLogin == 2) {
                    System.out.println("--Zadejte informace o letu--");

                } else if (tempLogin == 3) {
                    System.out.println("--Zadejte informace o letadle--");
                    System.out.println("Nazev letadla: ");
                    String name = sc.next();
                    System.out.println("Vyberte typ letadla: ");
                    generateTypesOfLicence();
                    int tempLicence = sc.nextInt() - 1;
                    System.out.println("Zadejte registraci letadla: ");
                    String reg = sc.next();
                    plane = new Plane(name, TypeOfLicence.valueOf(tempLicence), reg);

                }
            } while (login);
        } while (!end);
    }

    public static void generateTypesOfLicence() {
        for (int i = 0; i < TypeOfLicence.values().length; i++) {
            System.out.println(i + 1 + "." + " - " + TypeOfLicence.valueOf(i));
        }
    }
}
