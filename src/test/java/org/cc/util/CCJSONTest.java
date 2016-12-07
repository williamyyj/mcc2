/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.util;

import static org.cc.CCBaseTest.base;
import org.cc.ICCList;
import org.cc.ICCMap;
import org.junit.Test;

/**
 *
 * @author william
 */
public class CCJSONTest {

    @Test
    public void load_json() {
        ICCMap cfg = CCJSON.load(base, "cfg");

        CCLogger.debug(cfg.map("sms").toString(4));
        ICCList list = new CCJsonParser("[1,2,3,4,5,true,'this is a book',中文]").parser_list();
        CCLogger.debug(CCJSON.toString(list, 4));
    }

}
