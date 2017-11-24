package org.cc.fun.db;


import org.cc.CCProcObject;
import org.cc.CCTest;
import org.cc.util.CCFunc;
import org.junit.Test;

/**
 *
 * @author william
 */
public class FMDTest {

    @Test
    public void test_jometadata() throws Exception {
        CCProcObject proc = new CCProcObject(CCTest.prj_base);
        try {                  
            String metaId = "JOContent";
            proc.params().put("table", metaId);
            System.out.println("-----------------------------------------------------");
            System.out.println(proc.params());
            System.out.println("-----------------------------------------------------");
            CCFunc.apply("db.proc_metadata", proc);
           // System.out.println(new UMLClass(proc.base(), metaId).toERDString());
            //GDocDao.gen_schema(metaId);
        } finally {
            proc.release();
        }
    }
}
