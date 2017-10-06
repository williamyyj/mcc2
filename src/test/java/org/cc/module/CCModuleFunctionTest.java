package org.cc.module;

import org.cc.CCList;
import org.cc.CCMap;
import org.cc.CCTest;
import org.cc.ICCList;
import org.cc.ICCMap;
import org.cc.util.CCJSON;
import org.cc.util.CCPath;
import org.junit.Test;

/**
 * @author william
 */
public class CCModuleFunctionTest {

    @Test
    public void test_meta() {
        String pageId = "list";
        String moduleId = "rpt_retsp";
        CCModule cm = new CCModule(CCTest.prj_base, moduleId);
        try {
            cm.inject(CCJSON.line("theme:jsp.admin,ht:tags"));
            ICCMap vm = CCJSON.data(cm.cfg(), CCPath.map(cm.cfg(), "$cell:" + pageId));
            ICCList list = vm.list("$fields");
            ICCList meta = new CCList();
            list.forEach( o->{
                ICCMap m =  (ICCMap) o;
                ICCMap item = new CCMap();
                CCJSON.mix(item, m, new String[]{"id","label","ft"});
                System.out.println(item);
            });
        } finally {
            cm.release();
        }
    }

}
