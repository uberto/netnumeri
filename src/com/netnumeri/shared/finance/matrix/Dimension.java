package com.netnumeri.shared.finance.matrix;

import java.io.Serializable;

public class Dimension implements Serializable {
    public Dimension() {
        this(0, 0);
    }

    public Dimension(Dimension dimension) {
        this(dimension.width, dimension.height);
    }

    public Dimension(int i, int j) {
        width = i;
        height = j;
    }

    public double getWidth() {
        return (double) width;
    }

    public double getHeight() {
        return (double) height;
    }

    public void setSize(double d, double d1) {
        width = (int) Math.ceil(d);
        height = (int) Math.ceil(d1);
    }

    public Dimension getSize() {
        return new Dimension(width, height);
    }

    public void setSize(Dimension dimension) {
        setSize(dimension.width, dimension.height);
    }

    public void setSize(int i, int j) {
        width = i;
        height = j;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Dimension) {
            Dimension dimension = (Dimension) obj;
            return width == dimension.width && height == dimension.height;
        } else {
            return false;
        }
    }

    public int hashCode() {
        int i = width + height;
        return (i * (i + 1)) / 2 + width;
    }

    public String toString() {
        return (new StringBuilder()).append(getClass().getName()).append("[width=").append(width).append(",height=").append(height).append("]").toString();
    }

    public int width, height;
    private static final long serialVersionUID = 4723952579491349524L;

}
