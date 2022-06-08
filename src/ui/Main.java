package ui;

import org.w3c.dom.ranges.RangeException;
import utils.IInputValid;
import utils.InputValid;

import java.io.FileNotFoundException;
import java.time.DateTimeException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, RangeException, DateTimeException {
        IInputValid iInputValid = new InputValid();
        UI ui = new UI(iInputValid);
        ui.Menu();
    }
}