package org.cc;

import java.util.ArrayList;
import java.util.Date;
import org.cc.util.CCCast;
import org.cc.util.CCJSON;

/**
 *
 * @author william
 */
public class CCList extends ArrayList<Object> implements ICCList {

    @Override
    public int asInt(int idx, int dv) {
        return CCCast._int(get(idx), dv);
    }

    @Override
    public long asLong(int idx, Long dv) {
        return CCCast._long(get(idx), dv);
    }

    @Override
    public double asDouble(int idx, double dv) {
        return CCCast._double(get(idx), dv);
    }

    @Override
    public Date asDate(int idx) {
        return CCCast._date(get(idx));
    }

    @Override
    public String asString(int idx, String dv) {
        return CCCast._string(get(idx));
    }

    @Override
    public  String toString(){
        return CCJSON.toString(this);
    }
}
