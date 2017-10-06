package org.cc.ff;


import java.io.Serializable;
import org.cc.CCProcObject;
import org.cc.ICCMap;
import org.mvel2.MVEL;

/**
 *
 * @author william
 */
public class mvel<E> extends CCFFBase<E> {

    protected Serializable compileExpression;

    public mvel() {

    }

    @Override
    public void __init_proc(CCProcObject proc) {
        compileExpression = MVEL.compileExpression(cfg.asString("$cmd"));
    }

    @Override
    protected E apply(ICCMap row, String id, Object fv) {
        row.put("$fv", fv);
        row.put("$id", id);
        E ret = (E) MVEL.executeExpression(compileExpression, row);
        row.remove("$fv");
        row.remove("$id");
        return ret ;
    }
}
