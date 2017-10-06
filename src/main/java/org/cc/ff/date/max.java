package org.cc.ff.date;

import java.util.Calendar;
import java.util.Date;
import org.cc.CCProcObject;
import org.cc.ICCMap;
import org.cc.ff.CCFFBase;
import org.cc.util.DateUtil;

/**
 *
 * @author william
 */
public class max extends CCFFBase<Date> {

    @Override
    public void __init_proc(CCProcObject proc) {

    }

    @Override
    protected Date apply(ICCMap row, String id, Object o) {
        Date d = DateUtil.to_date(row.get(id));
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

}
