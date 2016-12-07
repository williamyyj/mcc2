package org.cc;

import org.cc.db.DB;

/**
 * @author william
 */
public class CCProcObject extends CCMap {

    public final static String proc_db = "$db";

    private DB db;
    
    private String base;

    public CCProcObject(String base) {
        this.base = base;
    }

    public DB db() {
        return (DB) this.getOrDefault(proc_db, init_db());
    }

    private Object init_db() {
        if (db == null) {
            db = new DB(base);
            put(proc_db,db);
        }
        return db;
    }

}
