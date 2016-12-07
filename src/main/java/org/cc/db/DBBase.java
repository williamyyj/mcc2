package org.cc.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.cc.CCConfig;
import org.cc.CCMap;
import org.cc.ICCMap;
import org.cc.IDP;
import org.cc.type.CCTypes;
import org.cc.util.CCJSON;
import org.cc.util.CCLogger;

/**
 *
 * @author william
 */
public abstract class DBBase implements IDP<Connection> {

    protected static int connCount;

    protected static Map<String, ICCDataSource> mds;
    protected String base;
    protected Connection conn;
    protected ICCMap cfg;
    protected boolean is_reference = false;
    protected CCTypes types;

    public DBBase(String base) {
        this(base, null, null);
    }

    public DBBase(String base, String cfgId, String oid) {
        this.base = (base == null) ? System.getProperty("base") : base;
        ICCMap root = (cfgId != null) ? CCJSON.load(base, cfgId) : CCJSON.load(base, "cfg");
        oid = (oid == null) ? "db" : oid;
        cfg = root.map(oid);
        if (cfg == null) {
            cfg = new CCConfig(base, oid).params();
            if (cfg == null) {
                cfg = new CCMap();
            }
        }
        init_components();
    }

    public DBBase(String base, Connection conn) {
        this(base);
        if (conn != null) {
            is_reference = true;
            this.conn = conn;
        }
        init_components();
    }

    public DBBase(String base, ICCMap cfg) {
        this.base = base;
        this.cfg = cfg;
        init_components();
    }

    protected void init_components() {
        cfg.put("base", this.base);
        // will using  JOEval 
        if (cfg.containsKey("url")) {
            String url = (String) cfg.getOrDefault("url", "");
            url = url.replace("${base}", this.base);
            cfg.put("url", url);
        }
    }


    @Override
    public Connection connection() throws Exception {
        if (conn == null) {
            CCLogger.debug(ds());
            conn = ds().getConnection();
            connCount++;
        }
        return conn;
    }

    protected String id() {
        return (String) cfg.getOrDefault("id", "db");
    }

    protected ICCDataSource ds() {
        String id = id();
        ICCDataSource ds = mds().get(id);
        if (ds == null) {
            String classId = cfg.asString("ds", "org.cc.db.DSTomcatPool");
            CCLogger.info("classId : " + classId);
            try {
                ds = (ICCDataSource) Class.forName(classId).newInstance();
                ds.init(cfg);
                mds().put(id, ds);
            } catch (Exception ex) {
                CCLogger.error("", ex);
            }
        }
        return ds;
    }

    protected Map<String, ICCDataSource> mds() {
        if (mds == null) {
            mds = new HashMap();
        }
        return mds;
    }

    protected void __release(Connection conn) throws SQLException {
        if (conn != null) {
            conn.close();
            connCount--;
            conn = null;
        }
    }


    @Override
    public void release() {
        try {
            if (!is_reference) {
                __release(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public ICCMap cfg() {
        return cfg;
    }


    public String base() {
        return base;
    }

    public void shutdown() throws Exception {
        if (mds != null) {
            Set<Map.Entry<String, ICCDataSource>> entry = mds.entrySet();
            for (Map.Entry<String, ICCDataSource> e : entry) {
                e.getValue().close();
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append("\n");
        sb.append(ds().toString()).append("\n");
        sb.append(ds().info());
        return sb.toString();
    }

}
