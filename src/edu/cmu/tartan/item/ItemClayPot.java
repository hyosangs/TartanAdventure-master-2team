package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Destroyable;
import edu.cmu.tartan.properties.Holdable;
import edu.cmu.tartan.properties.Hostable;
import edu.cmu.tartan.util.IPrintOut;
import edu.cmu.tartan.util.IScannerIn;

/**
 * A clay pot can be destroyed, held, and host other objects
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class ItemClayPot extends Item implements Destroyable, Holdable, Hostable {


    private String destroyMessage;
    private boolean disappears;

    /**
     * Create a new clay pot
     * @param s description
     * @param sd long description
     * @param a aliases
     */
    public ItemClayPot(String s, String sd, String[] a) {
        super(s, sd, a);
        setValue(3);
    }

    public ItemClayPot(String s, String sd, String[] a, IScannerIn scannerIn, IPrintOut printOut) {
        super(s, sd, a);
        setValue(3);

        super.printOut = printOut;
        super.scannerIn = scannerIn;
    }

    /**
     * Message to display when breaking the pot
     * @param s the message
     */
    @Override
    public void setDestroyMessage(String s) {
        this.destroyMessage = s;
    }

    /**
     * Break the pot
     */
    @Override
    public void destroy() {
        super.printOut.console(destroyMessage);
    }

    /**
     * Sets whether pot should disappear
     * @param b set to true if the item should disappear
     */
    @Override
    public void setDisappears(boolean b) {
        this.disappears = b;
    }

    /**
     * Make the pot vanish
     * @return
     */
    @Override
    public boolean disappears() {
        return this.disappears;
    }
}
