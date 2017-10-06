package org.cc.fun.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.BiFunction;
import org.cc.CCMap;
import org.cc.ICCList;
import org.cc.ICCMap;
import org.cc.ICCType;
import org.cc.util.CCLogger;

/**
 *
 * @author william
 */
public class bf_rs_load implements BiFunction<ResultSet, ICCList, ICCMap> {

    @Override
    public ICCMap apply(ResultSet rs, ICCList rsFields) {
        ICCMap row = new CCMap();
        rsFields.stream().forEach(o -> {
            try {
                ICCMap meta = (ICCMap) o;
                ICCType type = (ICCType) meta.get("type");
                String name = meta.asString("name");
                row.put(name, type.getRS(rs, name));

            } catch (SQLException ex) {
                CCLogger.error("fail : " + o.toString(), ex);
            }
        });
        return row;
    }

}
