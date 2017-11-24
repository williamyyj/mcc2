package org.cc.mvel;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.mvel2.templates.TemplateRuntime;

/**
 * @author william
 */
public class CCMvelTemplateTest {

    @Test
    public void test_line() {
        Map<String, Object> m = new HashMap<>();
        m.put("dp", "dp");
        m.put("did", "row");
        m.put("label", "a");
        //m.put("hvar","b");
        m.put("$", m);
        Object ret = TemplateRuntime.eval("$${@{did}.cName(@{did}.newId)}", m);
        System.out.println(ret);
        ret = TemplateRuntime.eval("@{$.?hvar==null ? label : hvar }", m);
        System.out.println(ret);

    }

}
