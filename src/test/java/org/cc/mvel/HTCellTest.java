package org.cc.mvel;

import java.util.HashMap;
import java.util.Map;
import org.cc.CCTest;
import org.junit.Test;

/**
 *
 * @author william
 */
public class HTCellTest {

    @Test
    public void test_load() {
        Map<String, Object> m = new HashMap<String, Object>();
        String text = "@ht_cell{['$tab':0,'tml':'page']}";
        m.put("ht","tags");
        m.put("theme", "jsp.admin");
        m.put("funId","rpt_retsp");
        CCMvelTemplate tml = new CCMvelTemplate(CCTest.prj_base, text.toCharArray());
        Object ret = tml.execute(m);
        System.out.println(ret);
    }
}
