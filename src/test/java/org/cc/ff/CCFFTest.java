package org.cc.ff;

import java.util.Date;
import org.cc.CCMap;
import org.cc.CCProcObject;
import org.cc.CCTest;
import org.cc.ICCMap;
import org.cc.model.CCFF;
import org.cc.model.CCMetadata;
import org.cc.model.ICCFF;
import org.cc.util.CCJSON;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author william
 */
public class CCFFTest {

    @Test
    public void test_main() throws Exception {
        CCProcObject proc = new CCProcObject(CCTest.base);
        try {
            test_fmt(proc);
            //test_dup(proc);
            //test_mapping(proc);
            //test_mvel(proc);
            //System.out.println("===== proc : " + proc.toString(4));
        } finally {
            proc.release();
        }
    }

    private void test_fmt(CCProcObject proc) {
        CCMetadata md = proc.metadata("ps_salesp_log");
        CCFF.proc_ff(proc, "$id:df1,$ff:df,$pattern:'yyyy/MM/dd HH:mm:ss'");
        CCFF.proc_ff(proc, "$id:dmax,$ff:date.max");
        ICCMap row = new CCMap();
        row.put("mem_ct", new Date());
        row.put("mem_mt", "2017/08/09");
        System.out.println(proc.ff("df1", row, "mem_ct", ""));
        System.out.println(proc.ff("df1", row, "mem_mt", ""));
        System.out.println(proc.ff("dmax", row, "mem_mt", ""));
    }

    private void test_dup(CCProcObject proc) {
        CCMetadata md = proc.metadata("ps_salesp_log");
        String line = "$id:df1,$ff:df,$pattern:'yyyy/MM/dd HH:mm:ss'";
        ICCFF ff = CCFF.proc_ff(proc, line);
        ICCMap row = new CCMap();
        row.put("mem_ct", new Date());
        Object ret = proc.ff("df1", row, "mem_ct", "");
        System.out.println("===== ret : " + ret);

    }

    private void test_mapping(CCProcObject proc) {

        String line = "$id:m_auditing,$ff:m,1:待審核,2:審核通過,3:申請失效,4:寄送光碟,5:結案";
        ICCFF ff = CCFF.proc_ff(proc, line);
        ICCMap row = new CCMap();
        row.put("auditing", 4);
        Object ret1 = ff.as(row, "auditing");
        Object ret2 = proc.ff("m_auditing", row, "auditing", "");
        Assert.assertEquals("寄送光碟", ret1);
        Assert.assertEquals("寄送光碟", ret2);
        Assert.assertEquals(ret1, ret2);
    }

    private void test_mvel(CCProcObject proc) {
        CCMetadata md = proc.metadata("ps_salesp_log");
        String line = "$id:barcode,$ff:mvel,$cmd:'barcode=\\'=\"\\'+$fv+\\'\"\\''";
        ICCFF ff = CCFF.ff_create(proc, md, line);
        ICCMap row = CCJSON.loadString("{barcode1:1234567890ABC}");
        ff.as(row, "barcode1");
        System.out.println(row.get("barcode"));
    }
}
