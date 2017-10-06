package org.cc.ff;

import org.cc.CCProcObject;
import org.cc.ICCInit;
import org.cc.ICCMap;
import org.cc.model.ICCFF;
import org.cc.util.CCPath;



/**
 *
 * @author william
 * @param <E>
 */
public abstract class CCFFBase<E> implements ICCFF<E>, ICCInit<ICCMap> {

    protected ICCMap cfg;
    protected String ffId;
    protected String alias;
    protected String pattern ;

    public CCFFBase() {

    }

    public abstract void __init_proc(CCProcObject proc);


    @Override
    public void init(CCProcObject proc) throws Exception {
        __init_proc(proc);
    }


    @Override
    public void __init__(ICCMap cfg) throws Exception {
        this.cfg = cfg;
        this.ffId = cfg.asString("$id");
        String[] items = ffId.split("_");
        this.alias = (items.length > 1) ? items[1] : ffId;
        this.pattern = cfg.asString("pattern");
    }

    protected Object getRowValue(ICCMap row, Object dv)  {
        String name = (String) CCPath.path(cfg, "$fld:name");
        Object ret = null;
        if (row.containsKey(ffId)) {
            ret = row.get(ffId);
        } else if (row.containsKey(name)) {
            ret = row.get(name);
        } else if (row.containsKey(alias)) {
            ret = row.get(alias);
        } else {
            ret = dv;
        }
        return (E) ret;
    }

    protected void set(String prefix, String suffix, ICCMap row, Object v) {
        String id = prefix + alias + suffix;
        row.put(id, v);
    }

    @Override
    public E as(ICCMap row, String id) {
        Object o = row.get(id);
        return apply(row,id,o);
    }
    
    @Override
    public ICCMap cfg(){
        return this.cfg;
    }
    
    protected abstract E apply(ICCMap row, String id, Object o);
    
}
