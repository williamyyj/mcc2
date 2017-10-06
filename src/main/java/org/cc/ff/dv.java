package org.cc.ff;

import org.cc.CCProcObject;
import org.cc.ICCMap;



/**
 *
 * @author william
 */
public class dv extends CCFFBase<String>  {

    @Override
    public void __init_proc(CCProcObject proc) {

    }

    @Override
    protected String apply(ICCMap row, String id, Object o) {
        return (o==null) ? "" : o.toString().trim();
    }
    
}
