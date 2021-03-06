package org.cc.fun.db;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.Set;
import java.util.function.Function;
import org.cc.CCList;
import org.cc.CCMap;
import org.cc.CCProcObject;
import org.cc.ICCList;
import org.cc.ICCMap;
import org.cc.data.CCDataUtils;
import org.cc.util.CCFunc;
import org.cc.util.CCJSON;
import org.cc.util.CCLogger;

/**
 * @author william
 */
public class proc_metadata extends DBFBase implements Function<CCProcObject, Boolean> {

    @Override
    public Boolean apply(CCProcObject proc) {
        ICCMap p = proc.params();
        String table = p.asString("table", null);
        if (table == null) {
            throw new RuntimeException("Can't find proc.params.table ");
        } else {
            try {
                ICCMap nm = exec(proc, table);
                proc_mix(new File(proc.base() + "/metadata", table + ".json"), nm);
            } catch (Exception e) {
                CCLogger.info("Fail proc_metadata : " + table, e);
            }
        }
        return true;
    }

    public ICCMap exec(CCProcObject proc, String table) throws Exception {
        ICCMap nm = new CCMap();
        ICCList ncols = new CCList();
        ResultSet rs = null;
        StringBuilder name = new StringBuilder();
        name.append(table).append(',');
        try {
            Set<String> pk = (Set<String>) CCFunc.apply("db.proc_pk", proc);
            rs = dbmd(proc).getColumns(catalog(proc), schema(proc), table, null);
            while (rs.next()) {
                ICCMap col = col(proc, rs);
                if (pk.contains(col.asString("name"))) {
                    col.put("ct", "P");
                }
                name.append(col.asString("id")).append(',');
                ncols.add(col);

            }
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
        name.setLength(name.length() - 1);
        nm.put("id", table);
        nm.put("name", table);
        nm.put(CCProcObject.meta_fields, name.toString());
        nm.put("meta", ncols);
        return nm;
    }

    private boolean get_is_null(ResultSet rs) throws Exception {
        String is_null = rs.getString("IS_NULLABLE");
        return ("Y".equalsIgnoreCase(is_null) || "YES".equalsIgnoreCase(is_null));
    }

    private ICCMap col(CCProcObject proc, ResultSet rs) throws Exception {
        ICCMap col = new CCMap();
        col.put("_indent", false);
        String name = rs.getString("COLUMN_NAME");
        col.put("name", name);
        col.put("id", proc.db().to_alias(name));
        int dt = rs.getInt("DATA_TYPE");
        col.put("dt", proc.db().types().type(dt).dt());
        col.put("jdbc", rs.getString("TYPE_NAME"));
        col.put("size", rs.getInt("COLUMN_SIZE"));
        col.put("note", "");
        col.put("label", "");
        if (!get_is_null(rs)) {
            col.put("ct", "M");
        }
        check_auto(col);
        //col.remove("jdbc");//  
        return col;
    }

    private void check_auto(ICCMap col) {
        String jdbc = col.asString("jdbc");
        if (jdbc.contains("identity")) {
            col.put("ft", "auto");
        }
    }

    private void proc_mix(File f, ICCMap nm) throws IOException {
        ICCMap ret = nm;
        if (f.exists()) {
            ICCMap om = CCJSON.load(f, "UTF-8");
            ICCList ocols = (ICCList) om.remove("meta");
            ICCList ncols = (ICCList) nm.remove("meta");
            ICCList rcols = new CCList();
            ret = CCJSON.mix(nm, om);
            ret.put(CCProcObject.meta_fields, nm.get(CCProcObject.meta_fields));
            for (int i = 0; i < ncols.size(); i++) {
                ICCMap nrow = ncols.map(i);
                ICCMap orow = CCJSON.get(ocols, "id", nrow.asString("id"));
                ICCMap row = CCJSON.mix(nrow, orow);
                if (orow != null) {
                    row.put("label", orow.get("label"));
                    row.put("note", orow.get("note"));
                    row.put("ct", orow.get("ct")); //  人工調整
                }
                rcols.add(row);
            }
            ret.put("meta", rcols);
        }
        CCDataUtils.saveString(f, "UTF-8", ret.toString(4));
    }

}
