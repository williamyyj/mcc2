package org.cc.codebase;

import java.io.File;
import java.io.IOException;
import org.cc.ICCList;
import org.cc.ICCMap;
import org.cc.data.CCDataUtils;
import org.cc.util.CCDateUtils;
import org.cc.util.CCJSON;
import org.mvel2.templates.TemplateRuntime;

/**
 *
 * @author william
 */
public class CCCodeBase {

    private ICCMap cb;
    private ICCMap cp;

    public CCCodeBase(String base, String id) {
        cb = CCJSON.load(base, "cb");
        cp = CCJSON.load(base + "/codebase", id);
    }

    public ICCMap cb() {
        return this.cb;
    }

    public ICCMap cp() {
        return this.cp;
    }

    public void list() {
        ICCList list = cp.list("code");
        ICCMap sm = CCJSON.data(cb, cb.map("$source"));
        ICCMap pm = CCJSON.data(cb, cb.map("$prod"));
        list.stream().forEach((o) -> {
            File fsrc = null;
            File fdest = null;
            long lastModifiedTime = CCDateUtils.toDate(cp.asString("date")).getTime();
            String src = (String) TemplateRuntime.eval((String) o, sm);
            src = src.replaceAll("\\\\", "/");
            fsrc = new File(src);
            String prod = (String) TemplateRuntime.eval((String) o, pm);
            prod = (String) TemplateRuntime.eval(prod, cp);
            fdest = new File(prod);
            System.out.println(fdest);
        });
    }

    public void pack() {
        ICCList list = cp.list("code");
        ICCMap sm = CCJSON.data(cb, cb.map("$source"));
        ICCMap tm = CCJSON.data(cb, cb.map("$target"));
        System.out.println(tm.toString(4));
        list.stream().forEach((o) -> {
            File fsrc = null;
            File fdest = null;
            long lastModifiedTime = CCDateUtils.toDate(cp.asString("date")).getTime();
            try {
                String src = (String) TemplateRuntime.eval((String) o, sm);
                src = src.replaceAll("\\\\", "/");
                fsrc = new File(src);
                String target = (String) TemplateRuntime.eval((String) o, tm);
                target = (String) TemplateRuntime.eval(target, cp);
                fdest = new File(target);
                CCDataUtils.safe_dir(fdest.getParentFile());
                CCDataUtils.copy(fsrc, fdest);
                fdest.setLastModified(lastModifiedTime);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }
}
