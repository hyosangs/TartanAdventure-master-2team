package edu.cmu.tartan.item;

import edu.cmu.tartan.properties.Hostable;
import edu.cmu.tartan.properties.Openable;
import edu.cmu.tartan.room.RoomLockable;

/**
 * This class for a brick, which can hold a key and be opened.
 * <p>
 * Project: LG Exec Ed SDET Program
 * 2018 Jeffrey S. Gennari
 * Versions:
 * 1.0 March 2018 - initial version
 */
public class ItemLock extends Item implements Hostable, Openable {

    /**
     * Constructor
     * @param s description
     * @param sd long description
     * @param a aliases
     */
    public ItemLock(String s, String sd, String[] a) {

        super(s, sd, a);
        setValue(100);
    }

    /**
     * Open the lock to the locked room. Note the key must be installed in the lock to open
     * @return true if opened; false otherwise
     */
    @Override
    public Boolean open() {
        if (this.relatedRoom instanceof RoomLockable) {
            ((RoomLockable) this.relatedRoom).unlock(super.installedItem());
            return true;
        }
        return false;
    }
}
