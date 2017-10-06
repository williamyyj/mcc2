package org.cc.ff;

import org.cc.CCProcObject;
import org.cc.ICCMap;



/**
 *
 * @author william
 */
public class m  extends CCFFBase<String> {
    
    @Override
    public void __init_proc(CCProcObject proc) {

    }

    @Override
    protected String apply(ICCMap row, String id, Object fv) {
        if(fv !=null){
            String key = fv.toString().trim();
            return cfg.asString(key);
        }
        return "";
    }
    
}
