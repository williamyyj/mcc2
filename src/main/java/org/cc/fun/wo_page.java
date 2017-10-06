package org.cc.fun;

import java.util.List;
import java.util.function.Function;
import org.cc.CCMap;
import org.cc.CCProcObject;
import org.cc.ICCMap;
import org.cc.db.DBCmd;
import org.cc.db.IDB;
import org.cc.model.CCWorkObject;
import org.cc.util.CCFunc;
import org.cc.util.CCLogger;

/**
 *
 * @author william
 */
public class wo_page implements Function<CCWorkObject, List<ICCMap>> {

    @Override
    public List<ICCMap> apply(CCWorkObject wo) {
        try {
            CCProcObject proc = wo.proc();
            //JOLogger.debug("===== before "+wp.p());
            wo.reset((ICCMap) CCFunc.apply("util.m_form_whitespace", wo.p()));
            //JOLogger.debug("===== after   "+wp.p());
            ICCMap jq = wo.p();
            int page = jq.asInt("page", 1);  //
            int numPage = jq.asInt("numPage", 10);
            String orderby = wo.event().asString(CCWorkObject.orderby);
            ICCMap op = new CCMap(jq);  // 查詢字串
            // JOFunctional.exec("wp_before", wp);
            ICCMap mq = DBCmd.parser_cmd(wo);
            int rowCount = rowCount(proc.db(), mq);
            proc.set(CCProcObject.attr_request, "total", rowCount);
            proc.set(CCProcObject.attr_request, "page", page);
            proc.set(CCProcObject.attr_request, "numPage", numPage);
            op.remove("page");  //移除查詢字串
            op.remove("numPage"); //移除查詢字串
            proc.set(CCProcObject.attr_request, "ejo", CCFunc.apply("ende.zip_encode", op.toString())); // 查詢參數
            List<ICCMap> data = rows(proc.db(), mq, numPage, page, orderby);
            //JOFunctional.exec("wp_after", wp);
            return data;
        } catch (Exception ex) {
            CCLogger.error("Fail wo_page", ex);
        }
        return null;
    }

    private List<ICCMap> rows(IDB db, ICCMap mq, int num, int pageId, String orderby) throws Exception {
        ICCMap m = new CCMap(mq);
        String sql = mq.asString(CCWorkObject.wo_sql);
        StringBuilder sb = new StringBuilder();
        sb.append(" select t.* from ");
        sb.append(" ( select ROW_NUMBER() OVER (ORDER BY ").append(orderby).append(" )  rowid , ");
        sb.append("  c.* from ( ");
        sb.append(sql);
        sb.append(" ) c ) t ");
        sb.append(" where rowid > ").append(+(pageId - 1) * num);
        sb.append(" and rowid <= ").append((pageId) * num);
        m.put(CCWorkObject.wo_sql, sb.toString());
        return (List<ICCMap>) CCFunc.apply2("db.rows", db, m);
    }

    protected int rowCount(IDB db, ICCMap mq) {
        try {
            ICCMap m = new CCMap(mq);
            String sql = mq.asString(CCWorkObject.wo_sql);
            sql = "select count(*) from ( " + sql + " ) t ";
            m.put(CCWorkObject.wo_sql, sql);
            return (Integer) CCFunc.apply2("db.fun", db, m);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    protected int pages(IDB db, ICCMap mq, int num) {
        int count = rowCount(db, mq);
        int ret = count / num;
        return (ret * num == count) ? ret : ret + 1;
    }

}
