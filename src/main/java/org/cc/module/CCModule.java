package org.cc.module;

import java.util.HashMap;
import java.util.Map;
import org.cc.CCList;
import org.cc.CCMap;
import org.cc.CCProcObject;
import org.cc.ICCList;
import org.cc.ICCMap;
import org.cc.ICCResource;
import org.cc.model.CCFieldUtils;
import org.cc.model.CCMetadata;
import org.cc.model.ICCField;
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

    private ICCMap mFields;

    private ICCMap pFields;

    private String pre_module = "/module";

    private String pre_metadata = "/module/meta";

    public CCModule(String base, String moduleId) {
        this.base = base;
        this.moduleId = moduleId;
        __init_module();
    }

    private void __init_module() {
        mFields = new CCMap();
        cfg = CCJSON.load(base + pre_module, moduleId);
        proc = new CCProcObject(base);
        __init_metadata();
        __init_fields();
    }

    private void __init_fields() {
        ICCList list = (ICCList) cfg.remove("$fields");
        pFields = new CCMap();
        list.stream().forEach(o -> {
            ICCMap fld = (ICCMap) o ;
            pFields.put(fld.asString("id"),CCFieldUtils.mix(mFields,fld));
        });
    }

    private void __init_metadata() {
        ICCList mIds = cfg.list("$metadata");
        mIds.stream().map((o) -> (String) o).forEach((item) -> {
            CCMetadata md = proc.metadata(pre_metadata, item);
            md.fields().forEach((id,fld)->{
                mFields.put(id, fld.cfg());
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

    public ICCMap mFields() {
        return this.mFields;
    }

    public ICCMap getDataPool(String pageId) {
        ICCMap vm = CCJSON.data(cfg(), CCPath.map(cfg(), "$cell:" + pageId));
        vm.put("$cm", cfg());
        ICCList list = (ICCList) vm.remove("$fields");
        ICCList nFields = new CCList();
        list.stream().forEach(o -> {
            nFields.add(CCFieldUtils.mix(pFields, o));
        });
        vm.put("$fields", nFields);
        return vm;
    }

}
