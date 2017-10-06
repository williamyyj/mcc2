/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.ff;


import java.text.SimpleDateFormat;
import java.util.Date;
import org.cc.CCProcObject;
import org.cc.ICCMap;
import org.cc.util.DateUtil;


/**
 *  line   $id:df1,$ff:df,$pattern:'yyyy/MM/dd HH:mm:ss'  
  json  { "$id":"df1" ..... }  
   $ff(df , $ , 'mem_ct') 
 * @author william
 */
public class df extends CCFFBase<String> {

    private SimpleDateFormat sdf;

    @Override
    public void __init_proc(CCProcObject proc) {
        sdf = new SimpleDateFormat(cfg.asString("$pattern"));
    }

    @Override
    protected String apply(ICCMap row, String id, Object fv) {
        if(fv instanceof Date){
            return sdf.format((Date)fv);
        } else if(fv instanceof String){
            return sdf.format(DateUtil.to_date((String)fv));
        }
        return "";
    }

}
