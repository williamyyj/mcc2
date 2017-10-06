package org.cc.fun.db;

import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.cc.CCProcObject;
import org.cc.util.CCLogger;


public class DBFBase {

    public DatabaseMetaData dbmd(CCProcObject proc) throws Exception {
        DatabaseMetaData dbmd = (DatabaseMetaData) proc.get("$dbmd");
        if (dbmd == null) {
            dbmd = proc.db().connection().getMetaData();
            proc.put("$dbmd", dbmd);
        }
        return dbmd;
    }

    public String catalog(CCProcObject proc) {
        return proc.params().asString("catalog", proc.db().catalog());
    }

    public String schema(CCProcObject proc) {
        return proc.params().asString("schema", proc.db().schema());
    }
    
    public void __release(PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException ex) {
                CCLogger.error(ex);
            }
        }
    }

    public void __release(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                CCLogger.error(ex);
            }
        }
    }
}
