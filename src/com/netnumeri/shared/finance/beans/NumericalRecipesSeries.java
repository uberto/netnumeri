package com.netnumeri.shared.finance.beans;


import com.netnumeri.shared.finance.date.TDay;

public class NumericalRecipesSeries {

    float[] data;
    TDay[] dates;

    public NumericalRecipesSeries() {
    }

    public float[] getData() {
        return data;
    }

    public void setData(float[] data) {
        this.data = data;
    }

    public void setValue(int index, float data) {
        this.data[index] = data;
    }

    public void setDate(int index, TDay data) {
        this.dates[index] = data;
    }

    public float getValue(int index) {
        return this.data[index];
    }

    public TDay getDate(int index) {
        return this.dates[index];
    }

    public TDay[] getDates() {
        return dates;
    }

    public void setDates(TDay[] dates) {
        this.dates = dates;
    }

    public int getSize() {
        return data.length - 1;
    }

    public double getLastValue() {
        return getValue(data.length - 1);
    }

//    public Date getLastDate() {
//        return getDate(data.length - 1);
//    }

}