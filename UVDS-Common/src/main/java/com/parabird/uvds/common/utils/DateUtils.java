package com.parabird.uvds.common.utils;

import java.sql.Timestamp;

public class DateUtils {
    //We use ISO 8601 time for entire project
    public static final String dateFormat = "yyyy-MM-dd'T'HH:mm:ssZ";

    public static Timestamp getCurrentTime() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static Long getTimeDiffInMin(Timestamp t1, Timestamp t2) {
        long diff = t2.getTime() - t1.getTime();
        return diff / (60 * 1000);
    }


}
