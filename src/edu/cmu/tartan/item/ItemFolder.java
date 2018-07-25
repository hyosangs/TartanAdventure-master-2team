package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Openable;
import edu.cmu.tartan.util.IPrintOut;
import edu.cmu.tartan.util.IScannerIn;

/**
 * This class for a folder, which can be opened.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class ItemFolder extends Item implements Openable {

    private String openMessage;

    /**
     * Constructor
     * @param s description
     * @param sd long description
     * @param a aliases
     */
    public ItemFolder(String s, String sd, String[] a) {

        super(s, sd, a);
        setValue(3);
    }

    public ItemFolder(String s, String sd, String[] a, IScannerIn scannerIn , IPrintOut printOut) {

        super(s, sd, a);
        setValue(3);
        super.printOut = printOut;
        super.scannerIn = scannerIn;
    }

    /**
     * Open the folder
     * @return true when the folder is opened
     */
    @Override
    public Boolean open() {

        super.printOut.console(this.openMessage);
        return true;
    }

    /**
     * The message to display when the folder is opened.
     * @param o
     */
    public void setOpenMessage(String o) {
        this.openMessage = o;
    }

}
