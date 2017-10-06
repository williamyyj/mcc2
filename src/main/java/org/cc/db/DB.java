package org.cc.db;



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
    
    @Override
    public CCTypes types() {
        return types ;
     }





}
