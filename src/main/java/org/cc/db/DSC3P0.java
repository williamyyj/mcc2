package org.cc.db;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import org.cc.ICCMap;
import org.cc.util.CCLogger;

public class DSC3P0 implements ICCDataSource<ComboPooledDataSource> {

    private ICCMap cfg;
    private ComboPooledDataSource ds;

    @Override
    public void init(ICCMap cfg) {
        this.cfg = cfg;
    }

    @Override
    public String id() {
        return cfg.asString("id","db");
    }

    @Override
    public ComboPooledDataSource getDataSource() {
        if (ds == null) {
            try {
                ds = new ComboPooledDataSource();
                ds.setUser(cfg.asString("user"));
                ds.setPassword(cfg.asString("password"));
                ds.setDriverClass(cfg.asString("driver"));
                ds.setJdbcUrl(cfg.asString("url"));
                ds.setMaxPoolSize(cfg.asInt("cp30.maxPoolSize", 35));
                ds.setMinPoolSize(cfg.asInt("cp30.minPoolSize", 10));
                ds.setAcquireIncrement(cfg.asInt("cp30.acquireIncrement", 0));
                ds.setMaxIdleTime(cfg.asInt("cp30.maxIdleTime", 300));
                ds.setMaxStatements(cfg.asInt("cp30.maxStatements", 0));
                ds.setIdleConnectionTestPeriod(cfg.asInt("cp30.idleConnectionTestPeriod", 300));
                ds.setMaxIdleTimeExcessConnections(cfg.asInt("cp30.maxIdleTimeExcessConnections", 600));
            } catch (PropertyVetoException ex) {
                CCLogger.error("Can't set c3p0.ComboPooledDataSource : ", ex);
            }
        }
        return ds;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            if (ds != null) {
                ds.close();
            }
        } finally {
            super.finalize();
        }
    }

    @Override
    public void close() throws Exception {
        ds.close();
    }

    @Override
    public String info() {
        return  (ds!=null) ? ds.toString() : "empty" ; 
    }

}
