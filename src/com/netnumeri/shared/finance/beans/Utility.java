package com.netnumeri.shared.finance.beans;


public class Utility {
    /**
     * Checks for equality of Objects, null-sensitive.
     */
    static boolean equalsWithNullCheck(Object a, Object b) {
        return a == b || (a != null && a.equals(b));
    }

}
