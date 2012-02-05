package com.netnumeri.shared.field;

import java.io.Serializable;

public class FieldAttributes implements Serializable {
    int maxLenght;
    String editMask;

    public FieldAttributes(String editMask, int maxLenght) {
        this.editMask = editMask;
        this.maxLenght = maxLenght;
    }

    public int getMaxLenght() {
        return maxLenght;
    }

    public String getEditMask() {
        return editMask;
    }
}
