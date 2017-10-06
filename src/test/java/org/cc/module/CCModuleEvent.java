package org.cc.module;

import org.cc.module.CCEvent;
import org.cc.module.CCModule;
import java.io.IOException;
import org.cc.CCTest;
import org.cc.util.CCJSON;
import org.junit.Test;

/**
 * @author william
 */
public class CCModuleEvent {

    @Test
    public void test_event() throws IOException {
        String ap_base = "D:\\will\\work\\eclipse\\baphiq\\baphiqAdmin\\WebContent\\WEB-INF\\views\\admin\\baphiq\\stat";
        String eventId = "query";
        String moduleId = "rpt_retsp";
        CCModule cm = new CCModule(CCTest.prj_base, moduleId);
        try {
            cm.inject(CCJSON.line("theme:jsp.admin,ht:tags"));
            CCEvent event = new CCEvent(cm,"query");
            System.out.println(event.cond());
        } finally {
            cm.release();
        }
    }
}
