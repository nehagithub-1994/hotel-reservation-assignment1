package com.panoramichotel.api.util;

import java.util.Date;
import java.util.Random;

public class Util {
    public static int daysBetween(Date d1, Date d2) {
        return (int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }

    public static Long getFiveDigitRandomNo() {
        Random r = new Random(System.currentTimeMillis());
        Long bookingId = (long) (10000 + r.nextInt(20000));
        return bookingId;
    }
}
