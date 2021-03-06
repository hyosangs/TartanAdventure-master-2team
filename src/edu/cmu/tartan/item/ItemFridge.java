package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Pushable;
import edu.cmu.tartan.room.RoomObscured;
import edu.cmu.tartan.util.PrintOutInterface;
import edu.cmu.tartan.util.ScannerInInterface;

/**
 * This class for a fridge, which can be pushed.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class ItemFridge extends Item implements Pushable {

    // indicates whether fridge has been pushed
    private boolean wasPushed;

    /**
     * Constructor for the fridge
     * @param s description
     * @param sd long description
     * @param a aliases
     */
    public ItemFridge(String s, String sd, String[] a) {
        super(s, sd, a);
        this.wasPushed = false;
        setValue(1);
    }

    public ItemFridge(String s, String sd, String[] a, ScannerInInterface scannerIn, PrintOutInterface printOut) {
        super(s, sd, a);
        this.wasPushed = false;
        setValue(1);

        super.printOut = printOut;
        super.scannerIn = scannerIn;
    }

    /**
     * Push the fridge out of the way to reveal an obscured room.
     */
    @Override
    public void push() {
        if (!this.wasPushed) {
            if (this.relatedRoom instanceof RoomObscured) {
                ((RoomObscured) this.relatedRoom).setObscured(false);
                super.printOut.console(((RoomObscured) this.relatedRoom).unobscureMessage());
            }
            this.wasPushed = true;
        }
    }

    /**
     * wasPushed getter
     * @return wasPushed
     */
    public boolean wasPushed() {
        return wasPushed;
    }
}