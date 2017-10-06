package org.cc.model.field;

import org.cc.IAProxyClass;
import org.cc.ICCMap;
import org.cc.type.CCStringType;



/**
 *
 * @author william
 */
@IAProxyClass(id = "field.obj")
public class JOOField extends JOBaseField<String> {

    @Override
    public void __init__(ICCMap cfg) throws Exception {
        super.__init__(cfg);
        type = new CCStringType();
    }

   

}
