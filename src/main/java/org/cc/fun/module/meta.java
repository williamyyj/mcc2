
package org.cc.fun.module;

import java.util.function.Function;
import org.cc.CCMap;
import org.cc.ICCMap;
import org.cc.module.CCModule;

/**
 * @author william
 */
public class meta implements Function<CCModule,ICCMap> {

    @Override
    public ICCMap apply(CCModule cm) {
        ICCMap ret = new CCMap();
        return ret ;
    }
    
}
