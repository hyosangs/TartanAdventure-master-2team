package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Explodable;
import edu.cmu.tartan.properties.Holdable;
import edu.cmu.tartan.room.RoomObscured;
import edu.cmu.tartan.util.ScannerInInterface;
import edu.cmu.tartan.util.PrintOutInterface;

/**
 * This class for dynamite, which can be held and explode.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class ItemDynamite extends Item implements Explodable, Holdable {
    // Indicates whether dynamite has been detonated
    private boolean exploded;

    /**
     * Constructor
     * @param s description
     * @param sd long description
     * @param a aliases
     */
    public ItemDynamite(String s, String sd, String[] a) {
        super(s, sd, a);
        this.exploded = false;
        setValue(25);
    }

    public ItemDynamite(String s, String sd, String[] a, ScannerInInterface scannerIn, PrintOutInterface printOut) {
        super(s, sd, a);
        this.exploded = false;
        setValue(25);

        super.printOut = printOut;
        super.scannerIn = scannerIn;
    }

    /**
     * Explode the dynamite. Can be used to clear the way to a room
     *
     * @return true when the explosion occurs
     */
    @Override
    public Boolean explode() {
        if (!this.exploded) {
            if (this.relatedRoom instanceof RoomObscured) {
                ((RoomObscured) this.relatedRoom).setObscured(false);
                super.printOut.console(((RoomObscured) this.relatedRoom).unobscureMessage());
            }
            this.exploded = true;
            this.detailDescription = "pile of smithereens";
        } else {
            super.printOut.console("The dynamite has already been detonated.");
        }
        return exploded;
    }

    public void setExplodeMessage(String s) {
        if (this.relatedRoom instanceof RoomObscured) {
            ((RoomObscured) this.relatedRoom).setUnobscureMessage(s);
        }
    }

    /**
     * Has the dynamite been detonated?
     * @return true if the detonation occurred
     */
    public Boolean getExploded() {
        return exploded;
    }
}
