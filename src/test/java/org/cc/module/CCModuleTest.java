package org.cc.module;

import java.io.IOException;
import org.cc.CCTest;
import org.cc.ICCMap;
import org.cc.mvel.CCMvelTemplate;
import org.cc.util.CCJSON;
import org.cc.util.CCPath;
import org.junit.Test;

/**
 * @author william
 */
public class CCModuleTest {

    @Test
    public void test_load() throws IOException {
        String ap_base = "D:\\will\\work\\eclipse\\baphiq\\baphiqAdmin\\WebContent\\WEB-INF\\views\\admin\\baphiq\\stat";
        System.out.println("===== chk prj_base ::: " + CCTest.prj_base);
        String pageId = "query";
        String server = "admin";
        String moduleId = "rpt_sellss";
        CCModule cm = new CCModule(CCTest.prj_base,server+"."+ moduleId);

        try {
            
            cm.inject(CCJSON.line("theme:jsp.admin,ht:tags"));
            ICCMap vm = CCJSON.data(cm.cfg(), CCPath.map(cm.cfg(), "$cell:" + pageId));
            vm.put("$cm", cm.cfg());
            String templateId = cm.cfg().asString("theme") + "." + vm.asString("$template");
            CCMvelTemplate tml = new CCMvelTemplate(CCTest.prj_base, templateId);
            ICCMap pm = cm.getDataPool(pageId);
            Object ret = tml.execute(pm);
            System.out.println("--------------------------------------------------------");
            System.out.println(ret);
            System.out.println("--------------------------------------------------------");
            moduleId="rpt_sell_store";
         //   CCDataUtils.saveString(new File(ap_base,moduleId+"_"+pageId+".jsp"), "UTF-8", (String) ret);
        } finally {
            cm.release();
        }
    }
}
