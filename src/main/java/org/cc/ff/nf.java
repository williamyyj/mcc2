package org.cc.ff;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import org.cc.CCProcObject;
import org.cc.ICCMap;

/**
 *
 * @author william
 */
public class nf extends CCFFBase<String> {

    private NumberFormat nf = null;

    @Override
    public void __init_proc(CCProcObject proc) {
        nf = new DecimalFormat(cfg.asString("$pattern"));
    }

    @Override
    protected String apply(ICCMap row, String id, Object fv) {
        if (fv instanceof Number) {
            return nf.format(fv);
        } else if (fv instanceof String) {
            String text = ((String) fv).trim();
            return nf.format(Double.parseDouble(text));
        }
        return "";
    }
}
