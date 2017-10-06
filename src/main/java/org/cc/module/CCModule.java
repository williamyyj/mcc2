package org.cc.module;

import org.cc.CCProcObject;
import org.cc.ICCList;
import org.cc.ICCMap;
import org.cc.ICCResource;
import org.cc.util.CCJSON;

/**
 * @author william prj 實際專案 project /
 */
public class CCModule implements ICCResource {

    private String base;

    private String moduleId;

    private CCProcObject proc;

    ICCMap cfg;

    private String pre_module = "/module";

    private String pre_metadata = "/module/meta";

    public CCModule(String base, String moduleId) {
        this.base = base;
        this.moduleId = moduleId;
        __init_module();
    }

    private void __init_module() {
        cfg = CCJSON.load(base + pre_module, moduleId);
        proc = new CCProcObject(base);
        __init_metadata();

    }

    private void __init_metadata() {
        ICCList mIds = cfg.list("$metadata");
        for(Object o : mIds){
          String item = (String) o;
          proc.metadata(pre_metadata, item);
        }
    }
    
    public void inject(ICCMap m){
        cfg.putAll(m);
    }

    public ICCMap cfg() {
        return this.cfg;
    }

    public CCProcObject proc() {
        return this.proc;
    }

    @Override
    public void release() {
        if (proc != null) {
            proc.release();
        }
    }

}