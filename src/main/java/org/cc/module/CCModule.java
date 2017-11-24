package org.cc.module;

import org.cc.CCList;
import org.cc.CCMap;
import org.cc.CCProcObject;
import org.cc.ICCList;
import org.cc.ICCMap;
import org.cc.ICCResource;
import org.cc.model.CCFieldUtils;
import org.cc.model.CCMetadata;
import org.cc.util.CCJSON;
import org.cc.util.CCPath;

/**
 * @author william prj 實際專案 project /
 */
public class CCModule implements ICCResource {

    private String base;

    private String moduleId;

    private CCProcObject proc;

    private ICCMap cfg;

    private CCMFields mFields;

    private String pre_module = "/module";

    private String pre_metadata = "/metadata";

    public CCModule(String base, String moduleId) {
        this.base = base;
        this.moduleId = moduleId;
        __init_module();
    }

    private void __init_module() {
        mFields = new CCMFields();
        cfg = CCJSON.load(base + pre_module, moduleId);
        proc = new CCProcObject(base);
        __init_metadata();
        __init_fields();
    }

    private void __init_fields() {
        ICCList list = (ICCList) cfg.remove("$fields");
        list.stream().forEach(o -> {
            mFields.add((ICCMap) o);
        });
    }

    private void __init_metadata() {
        ICCList mIds = cfg.list("$metadata");
        mIds.stream().map((o) -> (String) o).forEach((item) -> {
            CCMetadata md = proc.metadata(pre_metadata, item);
            md.fields().forEach((id, fld) -> {
                mFields.add(fld.cfg());
            });
        });
    }
    
    public void inject(ICCMap m) {
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

    public ICCMap getDataPool(String pageId) {
        ICCMap vm = CCJSON.data(cfg(), CCPath.map(cfg(), "$cell:" + pageId));
        vm.put("$cm", cfg());
        vm.put("$mFields",mFields);
        ICCList list = (ICCList) vm.remove("$fields");
        if (list != null) {
            CCMFields flds = new CCMFields();
            CCList ulist = new CCList();
            String cid =  vm.asString("cid",null);
            list.stream().forEach(o -> {
                ICCMap m = CCFieldUtils.mix(mFields.map(), o);
                flds.add(m);
                if(cid!=null){
                    ICCMap fld = CCJSON.data(m, cid);
                    fld.put("tml",fld.asString("tml",vm.asString("tml")));
                    ulist.add(fld);
                }
            });
            vm.put("$flds", flds);
            vm.put("$fields", ulist);       
        }
        return vm;
    }

}
