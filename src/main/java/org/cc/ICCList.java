/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.cc.util.CCJSON;

/**
 *
 * @author william
 */
public interface ICCList extends List<Object> {

    default int asInt(int idx) {
        return asInt(idx, 0);
    }

    public int asInt(int idx, int dv);

    default long asLong(int idx) {
        return asLong(idx, 0L);
    }

    public long asLong(int idx, Long dv);

    default double asDouble(int idx) {
        return asDouble(idx, 0.0);
    }

    public double asDouble(int idx, double dv);

    public Date asDate(int idx);

    default ICCMap asMap(int idx) {
        Object o = get(idx);
        return (o instanceof ICCMap) ? (ICCMap) o : null;
    }

    default ICCList asList(int idx) {
        Object o = get(idx);
        return (o instanceof ICCList) ? (ICCList) o : null;
    }

    default String asString(int idx) {
        return asString(idx, "");
    }

    public String asString(int idx, String dv);

    default String toString(int indent) {
        return CCJSON.toString(this, indent);
    }

}
