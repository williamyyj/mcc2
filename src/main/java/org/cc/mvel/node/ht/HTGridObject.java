package org.cc.mvel.node.ht;

import java.util.Map;
import org.cc.IAProxyClass;
import org.cc.ICCMap;
import org.cc.module.CCMFields;
import org.cc.util.CCJSON;
import org.mvel2.MVEL;
import org.mvel2.integration.VariableResolverFactory;
import org.mvel2.templates.TemplateRuntime;
import org.mvel2.templates.util.TemplateOutputStream;

/**
 *
 * @author william
 */
@IAProxyClass(id = "ht_grid")
public class HTGridObject extends HTCellBase {

    public HTGridObject() {

    }

    @Override
    public Object eval(TemplateRuntime runtime, TemplateOutputStream appender, Object ctx, VariableResolverFactory factory) {
        Map p = (Map) MVEL.eval(contents, ctx, factory);
        String status = (String) p.get("status");
        if ("head".equalsIgnoreCase(status)) {
            proc_head(runtime, appender, p, factory);
        } else if ("body".equalsIgnoreCase(status)) {
            proc_body(runtime, appender, p, factory);
        }
        return next != null ? next.eval(runtime, appender, ctx, factory) : null;
    }

    private void proc_head(TemplateRuntime runtime, TemplateOutputStream appender, Object ctx, VariableResolverFactory factory) {
        CCMFields fields = (CCMFields) factory.getVariableResolver("$flds").getValue();
        String cid = strFactory("cid", factory, "$gd");
        fields.list().stream().forEach((o) -> {
            ICCMap fld = CCJSON.data((ICCMap) o, cid);
            fld.put("description", fld.asString("description"));
            fld.put("tml", strCtxFactory("html", fld, factory));
            fld.put("attr", strCtxFactory("hattr", fld, factory));
            factory.createVariable("$", fld);
            if (fld.containsKey("fx")) {
                String content = processTemplate("grid", fld, factory);
                fld.put("$content", content);
                fld.put("tml", fld.get("fx"));
            }

            appender.append(cell("grid", fld, factory));
            appender.append("\r\n");
        });
    }

    private void proc_body(TemplateRuntime runtime, TemplateOutputStream appender, Object ctx, VariableResolverFactory factory) {
        CCMFields fields = (CCMFields) factory.getVariableResolver("$flds").getValue();
        String cid = strFactory("cid", factory, "$gd");
        fields.list().stream().forEach((o) -> {
            ICCMap fld = CCJSON.data((ICCMap) o, cid);
            fld.put("description", fld.asString("description"));
            fld.put("tml", strCtxFactory("dtml", fld, factory));
            fld.put("attr", strCtxFactory("dattr", fld, factory));
            fld.put("did", strCtxFactory("did", fld, factory));
            fld.put("$var", evalCxtFactory("dval", fld, factory));
            if (fld.containsKey("fx")) {
                String content = processTemplate("grid", fld, factory);
                fld.put("$content", content);
                fld.put("tml", fld.get("fx"));
            }
            System.out.println("=====body fld[ " + fld.asString("id") + "]" + fld);
            factory.createVariable("$", fld);
            appender.append(cell("grid", fld, factory));
            appender.append("\r\n");
        });
    }
}
