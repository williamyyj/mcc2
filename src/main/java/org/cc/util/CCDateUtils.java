package org.cc.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author william
 */
public class CCDateUtils {

    public static Date toDate(String fmt, String text) {
        try {
            return new SimpleDateFormat(fmt).parse(text);
        } catch (ParseException ex) {
            CCLogger.info("Can't parser date : " + text);
        }
        return null;
    }

    public static Date toDate(String text) {
        String sfmt = "yyyyMMdd";
        String lfmt = "yyyyMMddHHmmss";
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        if (text.contains("CST ")) {
            try {
                return sdf.parse(text);
            } catch (ParseException ex) {
                CCLogger.info("Can't parser date : " + text);
            }
        }

        String str = text.replaceAll("[^0-9\\.]+", "");
        int len = str.length();
        switch (len) {
            case 7:
                return cdate(str);
            case 8:
                return toDate(sfmt, str);
            case 14:
                return toDate(lfmt, str);
        }
        return null;
    }
    
     public static Date cdate(Object text) {
        try {
            NumberFormat nf = new DecimalFormat("0000000");
            int dv = nf.parse(text.toString()).intValue();
            int year = (dv / 10000 + 1911) * 10000 + (dv % 10000);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            sdf.setLenient(false);
            return sdf.parse(String.valueOf(year));
        } catch (ParseException ex) {
            CCLogger.debug("Can't convet cdate : " + text);
            return null;
        }

    }
}
