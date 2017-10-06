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
        m.put("prefix", "row");
        Object ret = TemplateRuntime.eval("$${@{prefix}.cName(@{prefix}.newId)}", m);
        System.out.println(ret);

    }

}
