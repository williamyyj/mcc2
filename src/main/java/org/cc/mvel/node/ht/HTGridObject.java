package org.cc.mvel.node.ht;

import java.util.Map;
import org.cc.IAProxyClass;
import org.cc.ICCList;
import org.cc.ICCMap;
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
        ICCList qf = (ICCList) factory.getVariableResolver("$fields").getValue();
        String html = (String) factory.getVariableResolver("html").getValue();
        String hattr = (String) factory.getVariableResolver("hattr").getValue();
        qf.stream().forEach((o) -> {
            ICCMap fld = (ICCMap) o;
            fld.put("placeholder", fld.asString("placeholder"));
            fld.put("description", fld.asString("description"));
            fld.put("id", fld.asString("alias", fld.asString("id")));
            fld.put("tml", fld.getOrDefault("html", html));
            fld.put("attr", fld.getOrDefault("hattr", hattr));
            //factory.createVariable("$meta", fld);
            appender.append(processTemplate("grid", fld, factory));
            appender.append("\r\n");
        });
    }

    private void proc_body(TemplateRuntime runtime, TemplateOutputStream appender, Object ctx, VariableResolverFactory factory) {
        ICCList qf = (ICCList) factory.getVariableResolver("$fields").getValue();
        String btml = (String) factory.getVariableResolver("btml").getValue();
        String battr = (String) factory.getVariableResolver("battr").getValue();
        qf.stream().forEach((o) -> {
            ICCMap fld = (ICCMap) o;
            fld.put("placeholder", fld.asString("placeholder"));
            fld.put("description", fld.asString("description"));
            fld.put("id", fld.asString("alias", fld.asString("id")));          
            fld.put("attr", fld.getOrDefault("battr", battr));
            if(fld.containsKey("beval")){    
                fld.put("eval",TemplateRuntime.eval(fld.asString("beval"), fld, factory));
                fld.put("btml", "eval");
            }
             fld.put("tml", fld.getOrDefault("btml", btml));
            appender.append(processTemplate("grid", fld, factory));
            appender.append("\r\n");
        });
    }
}
