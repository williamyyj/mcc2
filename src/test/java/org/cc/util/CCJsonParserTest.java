package org.cc.util;

import java.io.File;
import org.cc.CCBaseTest;
import org.cc.ICCList;
import org.cc.ICCMap;
import org.junit.Test;

/**
 *
 * @author william
 */
public class CCJsonParserTest extends CCBaseTest {
   
    @Test
    public void load_json() {
        File f = new File(base,"cfg.json");
        ICCMap m = new CCJsonParser(f,"UTF-8").parser_obj();
        CCLogger.debug(CCJSON.toString(m, 4));     
        ICCList list = new CCJsonParser("[1,2,3,4,5,true,'this is a book',\"中文\"]").parser_list();
        CCLogger.debug(CCJSON.toString(list,4));        
    }
    
}
