package org.cc.fun.util;

import java.util.function.Function;
import org.cc.CCMap;
import org.cc.ICCMap;

/**
 *
 * @author william
 */
public class m_form_whitespace implements Function<ICCMap, ICCMap> {

    @Override
    public ICCMap apply(ICCMap m) {
        ICCMap ret = new CCMap();
        m.forEach((n, v) -> {
            if(v instanceof String){
                String text = ((String)v).trim();
                if(text.length()>0){
                    ret.put(n, v);
                }
            } else {
                ret.put(n, v);
            }
        });
        return ret ;
    }

}
