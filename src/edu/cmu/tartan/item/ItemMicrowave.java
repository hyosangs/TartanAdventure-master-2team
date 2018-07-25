package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Hostable;
import edu.cmu.tartan.properties.Meltable;
import edu.cmu.tartan.properties.Startable;

/**
 * This class for a microwave, which can hold something and be started.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class ItemMicrowave extends Item implements Hostable, Startable {


    /**
     * Constructor
     * @param s description
     * @param sd long description
     * @param a aliases
     */
    public ItemMicrowave(String s, String sd, String[] a) {
        super(s, sd, a);
        setValue(5);
    }

    /**
     * Start the microwave. If the installed item is meltable, then melt it
     * @return true if started
     */
    @Override
    public Boolean start() {

        for (int i = 0; i < 3; i++) {
            super.printOut.console("...");
            try {
                Thread.sleep(1000);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        super.printOut.console("Beep beep beep");

        // Only meltable things can be microwaved
        if (super.installedItem() instanceof Meltable) {
            Item item = ((Meltable) super.installedItem()).meltItem();
            super.printOut.console("You melted the " + super.installedItem().detailDescription() + ", and it revealed a " + item.detailDescription() + "!");
            super.install(item);
            return true;
        }
        return false;
    }
}
