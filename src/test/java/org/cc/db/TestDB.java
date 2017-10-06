package org.cc.db;

import java.sql.SQLException;
import java.util.List;
import org.cc.CCMap;
import org.cc.CCProcObject;
import org.cc.CCTest;
import org.cc.ICCMap;
import org.cc.model.CCWorkObject;
import org.cc.util.CCFunc;
import org.junit.Test;

/**
 *
 * @author william
 */
public class TestDB {

    public void test_row() throws SQLException {

        CCProcObject proc = new CCProcObject(CCTest.base);
        try {
            ICCMap mq = new CCMap();
            mq.put(CCWorkObject.wo_sql, "select * from psRetail where newId = ? ");
            mq.put(CCWorkObject.wo_params, new Object[]{"I00040"});
            ICCMap row = (ICCMap) CCFunc.apply2("db.row", proc.db(), mq);
            System.out.println(row);
        } finally {
            proc.release();
        }
    }

    public void test_fun() throws SQLException {
        CCProcObject proc = new CCProcObject(CCTest.base);
        try {
            ICCMap mq = new CCMap();
            mq.put(CCWorkObject.wo_sql, "select count(*) from psRetail where newId = ? ");
            mq.put(CCWorkObject.wo_params, new Object[]{"I00040"});
            Object ret = CCFunc.apply2("db.fun", proc.db(), mq);
            System.out.println(ret);
        } finally {
            proc.release();
        }
    }

    public void test_rows() throws SQLException {

        CCProcObject proc = new CCProcObject(CCTest.base);
        try {
            ICCMap mq = new CCMap();
            mq.put(CCWorkObject.wo_sql, "select * from psRetail ");
            mq.put(CCWorkObject.wo_params, new Object[]{});
            List<ICCMap> rows = (List<ICCMap>) CCFunc.apply2("db.rows", proc.db(), mq);
            rows.stream().forEach(System.out::println);
        } finally {
            proc.release();
        }
    }

    @Test
    public void test_page() throws Exception {
        CCProcObject proc = new CCProcObject(CCTest.base);
        try {
            CCWorkObject wo = new CCWorkObject(proc, null);
            ICCMap event = new CCMap();
            event.put("$cmd", "select * from psRetail where 1=1 ${=,cityId,string}");
            event.put("$orderby", "newId");
            wo.setEvent(event);
            wo.p().put("cityId", "11");
            List<ICCMap> rows = (List<ICCMap>) CCFunc.apply("wo_page", wo);
            rows.stream().forEach(System.out::println);
            String ejo = (String) proc.get(CCProcObject.attr_request, "ejo", "");
            System.out.println(ejo);
            String decode = (String) CCFunc.apply("ende.zip_decode", ejo);
            System.out.println(decode);
        } finally {
            proc.release();
        }
    }
}
