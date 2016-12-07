/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc;

import java.util.Date;
import java.util.HashMap;
import org.cc.util.CCCast;
import org.cc.util.CCJSON;

/**
 *
 * @author william
 */
public class CCMap extends HashMap<String, Object> implements ICCMap {

    @Override
    public int asInt(String key, int dv) {
        return CCCast._int(get(key), dv);
    }

    @Override
    public long asLong(String key, Long dv) {
        return CCCast._long(get(key), dv);
    }

    @Override
    public double asDouble(String key, double dv) {
        return CCCast._double(get(key), dv);
    }

    @Override
    public String asString(String key, String dv) {
        return CCCast._string(get(key), dv);
    }

    @Override
    public Date asDate(String key) {
        return CCCast._date(get(key));
    }

    @Override
    public String toString() {
        return CCJSON.toString(this);
    }



}
