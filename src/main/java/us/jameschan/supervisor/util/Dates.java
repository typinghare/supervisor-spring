package us.jameschan.supervisor.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Dates {
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String toDateString(Timestamp timestamp) {
        if (timestamp == null) return null;

        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        return dateFormat.format(new Date(timestamp.getTime()));
    }
}
