package org.cc.db;


import org.cc.ICCMap;
import org.cc.type.CCTypes;

/**
 *
 * @author william
 */
public class DB extends DBBase {

    public DB(String base) {
        super(base);
    }

    @Override
    protected void init_components() {
        super.init_components();
        types = new CCTypes(cfg.asString("database"));
    }
    
    public CCTypes types() {
        return types ;
     }

    @Override
    public Object execute(ICCMap m) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
