package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Pushable;
import edu.cmu.tartan.util.PrintOutInterface;
import edu.cmu.tartan.util.ScannerInInterface;

/**
 * A button can be pushed
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class ItemButton extends Item implements Pushable {

    protected String pushMessage;

    public ItemButton(String s, String sd, String[] a) {
        super(s, sd, a);
        this.pushMessage = "Pushed.";
        setValue(2);
    }

    public ItemButton(String s, String sd, String[] a, ScannerInInterface scannerIn, PrintOutInterface printOut) {
        super(s, sd, a);
        this.pushMessage = "Pushed.";
        setValue(2);

        super.scannerIn = scannerIn;
        super.printOut = printOut;
    }

    /**
     * Push the button
     */
    @Override
    public void push() {
        super.printOut.console(this.pushMessage);
    }

    /**
     * Set the message to display when the button is pushed
     * @param s the message
     */
    public void setPushMessage(String s) {
        this.pushMessage = s;
    }


}
