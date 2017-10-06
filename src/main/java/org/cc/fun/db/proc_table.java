package org.cc.fun.db;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import org.cc.util.CCLogger;

/**
 *
 * @author William
 */
public class proc_table implements Function<Object[], List<String>> {

    @Override
    public List<String> apply(Object[] args) {
        try {
            DatabaseMetaData dbmd = (DatabaseMetaData) args[0];
            String catalog = (String) args[1];
            String schema = (String) args[2];
            return exec(dbmd, catalog, schema);
        } catch (Exception e) {
            CCLogger.info("Fail proc_tables  ", e);
        }
        return null;
    }

    public List<String> exec(DatabaseMetaData dbmd, String catalog, String schema) throws Exception {
        String[] DBTypes = {"TABLE"};
        ArrayList<String> list = new ArrayList<String>();
        ResultSet rs = null;
        try {
            rs = dbmd.getTables(catalog, schema, null, DBTypes);
            while (rs.next()) {
                String tbName = rs.getString("TABLE_NAME");
                // FIX oracle 
                if (!tbName.startsWith("BIN$")) {
                    list.add(tbName);
                }
            }
            return list;
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
    }

}
