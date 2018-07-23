package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Hostable;
import edu.cmu.tartan.properties.Openable;
import edu.cmu.tartan.PrintMessage;

import java.util.Scanner;

/**
 * This class for a safe, which can hold something and be opened.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class ItemSafe extends Item implements Hostable, Openable {


    // The safe's pin, which controls entry
    private Integer pin = null;

    public ItemSafe(String d, String sd, String[] a) {
        super(d, sd, a);
        this.pin = null;
        setValue(750);
    }

    /**
     * Set the safe PIN
     * @param pin the pin
     */
    public void setPIN(Integer pin) {
        this.pin = pin;
    }

    /**
     * Open the safe using the pin
     * @return true if the safe is successfully opened; false otherwise
     */
    @Override
    public Boolean open() {
        Scanner s = new Scanner(System.in);
        PrintMessage.printConsole("Enter the four-digit PIN number.");
        Integer p = Integer.parseInt(s.nextLine());
        if (p.intValue() == this.pin.intValue()) {

            super.installedItem().setVisible(true);
            PrintMessage.printConsole("The safe door swings open.");
            PrintMessage.printConsole("You have revealed a '" + super.installedItem().detailDescription() + "'.");

            return true;
        } else {
            PrintMessage.printConsole("Incorrect PIN.");
        }
        return false;
    }
}
