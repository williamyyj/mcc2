package org.cc.module;

import org.cc.CCList;
import org.cc.CCMap;
import org.cc.ICCList;
import org.cc.ICCMap;
import org.cc.util.CCJSON;

/**
 * Map + list +
 *
 * @author william
 */
public class CCMFields {

    private ICCList data = new CCList();
    private ICCMap mData = new CCMap();
    private String id = "id";

    public ICCList list() {
        return data;
    }

    public ICCMap map() {
        return mData;
    }

    public ICCMap get(int index) {
        return data.map(index);
    }

    public ICCMap get(String key) {
        return mData.map(key);
    }

    public boolean add(ICCMap m) {
        String key = (m != null) ? m.asString(id) : null;
        if (key != null) {
            ICCMap o = mData.map(key);
            if (o != null) {
                ICCMap n = CCJSON.mix(o, m);
                mData.put(key, n);
                return data.add(n);
            } else {
                mData.put(key, m);
                return data.add(m);
            }
        }
        return false;
    }

    public ICCMap remove(ICCMap m) {
        String key = (m != null) ? m.asString(id) : null;
        if (key != null) {
            boolean ret = data.remove(m);
            return (ICCMap) mData.remove(key);
        }
        return null;
    }

}
