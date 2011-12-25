package com.netnumeri.shared.finance.data;



import java.util.Date;
import java.util.LinkedHashMap;

public class CouponArray extends LinkedHashMap {
    public CouponArray() {
        super();
    }

    public void insert(Coupon c) {
        put(c.getDate(), c);
    }

    public Coupon getCoupon(Date date) {
        Coupon c = new Coupon();
        c.set((Coupon) get(date));
        return c;
    }

    public Coupon getCoupon(int i) {
        Coupon c = new Coupon();
        c.set((Coupon) get(i));
        return c;
    }

}
