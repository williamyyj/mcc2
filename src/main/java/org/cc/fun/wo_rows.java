package org.cc.fun;

import org.cc.fun.db.DBFBase;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.cc.ICCList;
import org.cc.ICCMap;
import org.cc.db.DBCmd;
import org.cc.model.CCWorkObject;
import org.cc.util.CCFunc;
import org.cc.util.CCLogger;

/**
 *
 * @author william
 */
public class wo_rows extends DBFBase implements Function<CCWorkObject, List<ICCMap>> {

    @Override
    public List<ICCMap> apply(CCWorkObject wo) {
        ICCMap mq = null;
        try {
            mq = DBCmd.parser_cmd(wo);
            return (List<ICCMap>) CCFunc.apply2("db.rows", wo.proc().db(), mq);
        } catch (Exception ex) {
            CCLogger.error("fail mq :" + mq, ex);
        }
        return null;
    }

}
