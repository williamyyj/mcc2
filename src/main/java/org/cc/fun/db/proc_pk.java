package org.cc.fun.db;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import org.cc.CCProcObject;
import org.cc.util.CCLogger;

/**
 * @author william
 */
public class proc_pk extends DBFBase implements Function<CCProcObject, Set<String>> {

    @Override
    public Set<String> apply(CCProcObject proc) {
        String catalog = proc.params().asString("catalog", proc.db().catalog());
        String schema = proc.params().asString("schema", proc.db().schema());
        String table = proc.params().asString("table");
        try {
            DatabaseMetaData dbmd = dbmd(proc);
            return exec(dbmd, catalog, schema, table);
        } catch (Exception e) {
            CCLogger.info("Fail procmd_pk : " + table, e);
        }
        return null;
    }

    public Set<String> exec(DatabaseMetaData dbmd, String catalog, String schema, String table) throws Exception {
        Set<String> list = new HashSet<String>();
        ResultSet rs = null;
        try {
            rs = dbmd.getPrimaryKeys(catalog, schema, table);
            while (rs.next()) {
                String name = rs.getString("COLUMN_NAME");
                list.add(name);
            }
            return list;
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
    }

}
