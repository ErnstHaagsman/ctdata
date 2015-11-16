package net.ctdata.datanode.utility;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Created by aditi on 15/11/15.
 */
public class DateTimeConversions {

    public static String getSQLTimestampString(Timestamp ts){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String str = format.format(ts);
            return str;
    }
}
