package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Edible;
import edu.cmu.tartan.properties.Valuable;
import edu.cmu.tartan.util.PrintOutInterface;
import edu.cmu.tartan.util.ScannerInInterface;

/**
 * This class for coffee cup, which can be eaten.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class ItemCoffee extends Item implements Edible, Valuable {

    /**
     * Constructor
     * @param s description
     * @param sd long description
     * @param a aliases
     */
    public ItemCoffee(String s, String sd, String[] a) {

        super(s, sd, a);
        setValue(1);
    }

    public ItemCoffee(String s, String sd, String[] a, ScannerInInterface scannerIn, PrintOutInterface printOut) {

        super(s, sd, a);
        setValue(1);

        super.scannerIn = scannerIn;
        super.printOut = printOut;
    }


    @Override
    public void eat() {
        super.printOut.console("You grimace at the taste of black coffee, and put down the mug.");
    }

}
