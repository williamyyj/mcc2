package org.cc;

import org.cc.util.CCJSON;



/**
 *
 * @author william
 */
public class CCConfig {

    private ICCMap pcfg; // public config
    private ICCMap cfg;

    public CCConfig(String base, String id) {
        init(base, id);
    }

    private void init(String base, String id) {
        pcfg = CCJSON.load(base, "cfg");
        if (pcfg != null) {
            cfg = CCJSON.load(base + "/config", id);
            String scope = pcfg.asString("scope");
            cfg = (cfg != null) ? cfg.map(scope) : null;
        }
        init_params();
    }

    private void init_params() {
        //  
    }

    public ICCMap params() {
        return this.cfg;
    }

}
