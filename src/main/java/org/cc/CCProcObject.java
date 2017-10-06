package org.cc;

import org.cc.db.DB;
import org.cc.db.IDB;
import org.cc.model.CCMetadata;
import org.cc.model.ICCFF;
import org.cc.util.CCPath;

/**
 * @author william
 */
public class CCProcObject extends CCMap implements ICCResource {

    public final static String pre_db = "$db";
    public final static String pre_metadata = "$metadata";
    public final static String pre_cfg = "$cfg";
    public final static String pre_ff = "$ff"; //  field function 
    public final static String pre_params = "$params"; //  field function 

    public final static String act = "$act";
    public final static String cmd = "$cmd";
    public final static int attr_self = 0;
    public final static int attr_params = 1;
    public final static int attr_request = 2;
    public final static int attr_session = 3;
    public final static int attr_app = 4;

    public final static String p_sql = "$p_sql";
    public final static String p_fields = "$p_fields";
    public final static String rs_fields = "$rs_fields";
    public final static String ps_fields = "$ps_fields";
    public final static String p_params = "$p_params";
    public final static String db_fields = "$db_fields";
    public final static String meta_fields = "$tbFields";

    protected String base;

    public CCProcObject(String base) {
        this.base = base;
        put(pre_params, new CCMap());
        put(pre_ff, new CCMap());
    }

    @Override
    public void release() {
        db().release();
    }

    public ICCMap params() {
        return map(pre_params);
    }

    /*
     *   monk object 
     */
    public Object get(int fld, String name, Object dv) {
        switch (fld) {
            case attr_self:
                return (containsKey(name)) ? get(name) : dv;
            case attr_params:
                return params().containsKey(name) ? params().get(name) : dv;
            case attr_request:
                return containsKey("$req_" + name) ? get("$req_" + name) : dv;
            case attr_session:
                return containsKey("$sess_" + name) ? get("$sess_" + name) : dv;
            case attr_app:
                return containsKey("$app_" + name) ? get("$app_" + name) : dv;
        }
        return dv;
    }

    public Object set(int fld, String name, Object value) {
        switch (fld) {
            case attr_self:
                return put(name, value);
            case attr_params:
                return params().put(name, value);
            case attr_request:
                return put("$req_" + name, value);
            case attr_session:
                return put("$sess_" + name, value);
            case attr_app:
                return put("$app_" + name, value);
        }
        return null;
    }

    public String base() {
        return this.base;
    }

    public IDB db() {
        return (IDB) this.getOrDefault(pre_db, init_db());
    }

    private Object init_db() {
        IDB db = (IDB) get(pre_db);
        if (db == null) {
            db = new DB(base);
            put(pre_db, db);
        }
        return db;
    }

    public CCMetadata metadata(String metaId) {
        String id = pre_metadata + ":" + metaId;
        CCMetadata md = (CCMetadata) CCPath.path(this, id);
        if (md == null) {
            md = new CCMetadata(base, metaId);
            CCPath.set(this, id, md);
        }
        return md;
    }

    public CCMetadata metadata(String prefix, String metaId) {
        String id = pre_metadata + ":" + metaId;
        CCMetadata md = (CCMetadata) CCPath.path(this, id);
        if (md == null) {
            md = new CCMetadata(base, prefix, metaId);
            CCPath.set(this, id, md);
        }
        return md;
    }

    public Object ff(String ffid, ICCMap row, String id, Object dv) {
        ICCFF ff = (ICCFF) map(pre_ff).get(ffid);
        return (ff == null) ? dv : ff.as(row, id);
    }
}
