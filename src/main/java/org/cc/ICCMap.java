package org.cc;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.cc.util.CCJSON;

/**
 *
 * @author william
 */
public interface ICCMap extends Map<String, Object> {

    default int asInt(String key) {
        return asInt(key, 0);
    }

    public int asInt(String key, int dv);

    default long asLong(String key) {
        return asLong(key, 0L);
    }

    public long asLong(String key, Long dv);

    default double asDouble(String key) {
        return asDouble(key, 0.0);
    }

    public double asDouble(String key, double dv);

    public Date asDate(String key);

    default ICCMap map(String key) {
        Object o = get(key);
        return (o instanceof ICCMap) ? (ICCMap) o : null;
    }

    default ICCList list(String key) {
        Object o = get(key);
        return (o instanceof ICCList) ? (ICCList) o : null;
    }

    default String asString(String key) {
        return asString(key, "");
    }

    public String asString(String key, String dv);

    default String toString(int indent) {
        return CCJSON.toString(this, indent);
    }

}
