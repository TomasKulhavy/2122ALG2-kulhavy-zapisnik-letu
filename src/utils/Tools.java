package utils;

import app.Pilot;
import app.Plane;
import app.TypeOfLicence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.List;

public class Tools {

    public Tools() {
    }

    public static void printPlanes(List<Plane> planeList) {
        for (int i = 0; i < planeList.size(); i++) {
            System.out.println(i + 1 + "." + " - " + planeList.get(i).getRegistration());
        }
    }

    public static void replaceSelected(File fileInput, String replaceOld, String replaceWith) {
        try {
            // input the file content to the StringBuffer "input"
            BufferedReader file = new BufferedReader(new FileReader(fileInput));
            StringBuffer inputBuffer = new StringBuffer();
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
}
