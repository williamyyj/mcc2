package org.cc.model;

import java.util.List;
import org.cc.util.CCFunc;
import org.cc.CCProcObject;
import org.cc.CCTest;
import org.cc.ICCMap;
import org.cc.util.CCJSON;
import org.junit.Test;

/**
 *
 * @author william
 */
public class TestCCWorkObject {

    @Test
    public void test_metadata() throws  Exception {
        CCProcObject proc = new CCProcObject(CCTest.base);
        try {
            CCWorkObject wo = new CCWorkObject(proc, null);
            String line = "{$cmd:'select top 10 * from psRetail '}";
            wo.p().put("newid", "I00040");
            wo.setEvent(CCJSON.loadString(line));
            List<ICCMap> rows = (List<ICCMap>) CCFunc.apply("wo_rows", wo);
            rows.stream().forEach(System.out::println);
        } finally {
            proc.release();
        }
    }

}
