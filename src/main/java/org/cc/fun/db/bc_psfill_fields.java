package org.cc.fun.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.function.BiConsumer;
import org.cc.ICCList;
import org.cc.ICCMap;
import org.cc.ICCType;

/**
 * @author william
 */
public class bc_psfill_fields implements BiConsumer<PreparedStatement, ICCList> {



    @Override
    public void accept(PreparedStatement ps, ICCList fields) {
        if (fields != null) {
            for (int i = 0; i < fields.size(); i++) {
                ICCMap fld = fields.map(i);
                ICCType type = (ICCType) fld.get("type");
                Object value = type.value(fld.get("value"));
                try {
                    type.setPS(ps, i + 1, value);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

}
