package org.cc.fun;

import org.cc.fun.db.DBFBase;
import java.util.function.Function;
import org.cc.ICCMap;
import org.cc.db.DBCmd;
import org.cc.model.CCWorkObject;
import org.cc.util.CCFunc;
import org.cc.util.CCLogger;

/**
 *
 * @author william
 */
public class wo_row extends DBFBase implements Function<CCWorkObject, ICCMap> {

    @Override
    public ICCMap apply(CCWorkObject wo) {
        ICCMap mq = null;
        try {
            mq = DBCmd.parser_cmd(wo);
            return (ICCMap) CCFunc.apply2("db.row", wo.proc().db(), mq);
        } catch (Exception ex) {
            CCLogger.error("fail mq :" + mq, ex);
        }
        return null;
    }

}
