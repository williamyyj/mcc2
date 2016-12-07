package org.cc.util;

import java.util.Date;
import org.junit.Test;

/**
 *
 * @author william
 */
public class CCCastTest {
    @Test
    public void to_date(){
        Date d = CCCast._date("20160101");
        System.out.println(CCCast._date(d.toString()));
    }
}
