package org.cc.fun.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.BiFunction;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.cc.ICCList;
import org.cc.ICCMap;
import org.cc.db.IDB;
import org.cc.model.CCWorkObject;
import org.cc.util.CCFunc;

/**
 *
 * @author william
 */
public class row extends DBFBase implements BiFunction<IDB, ICCMap, ICCMap> {

    @Override
    public ICCMap apply(IDB db, ICCMap mq) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = mq.asString(CCWorkObject.wo_sql);
            ps = db.connection().prepareStatement(sql);
            if (mq.containsKey(CCWorkObject.wo_fields)) {
                ICCList fields = mq.list(CCWorkObject.wo_fields);
                CCFunc.accept2("db.bc_psfill_fields", ps, fields);
            } else if (mq.containsKey(CCWorkObject.wo_params)) {
                Object[] params = (Object[]) mq.get(CCWorkObject.wo_params);
                CCFunc.accept2("db.bc_psfill_params", ps, params);
            }
            rs = ps.executeQuery();
            ICCList rsFields = (ICCList) CCFunc.apply2("db.bf_rs_metadata", db, rs);
            return (rs.next()) ? (ICCMap) CCFunc.apply2("db.bf_rs_load", rs, rsFields) : null;
        } catch (SQLException ex) {
            Logger.getLogger(row.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            __release(rs);
            __release(ps);
        }
        return null;
    }

}
