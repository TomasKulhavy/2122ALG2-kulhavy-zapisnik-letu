package ui;

import utils.IInputValid;
import utils.InputValid;

public class Main {
    public static void main(String[] args) {
        IInputValid iInputValid = new InputValid();
        UI ui = new UI(iInputValid);
        ui.Menu();
    }
}