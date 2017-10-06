package org.cc.mvel.node.ht;

import java.util.Map;
import org.cc.ICCList;
import org.cc.ICCMap;
import org.cc.mvel.CCMvelTemplate;
import org.cc.mvel.node.CCNode;
import org.mvel2.MVEL;
import org.mvel2.integration.VariableResolverFactory;
import org.mvel2.templates.TemplateRuntime;
import org.mvel2.templates.res.Node;
import org.mvel2.templates.util.TemplateOutputStream;

/**
 *
 * @author william
 */
public class HTCellBase extends CCNode {

    public HTCellBase() {

    }

    @Override
    public boolean demarcate(Node terminatingNode, char[] template) {
        return false;
    }

    protected String proc_pretty(Object o, String ht) {
        StringBuilder sb = new StringBuilder();
        if (ht != null) {
            int num = ((Number) o).intValue();
            char[] cbuf = ht.toCharArray();
            appendTab(sb, num);
            for (int i = 0; i < cbuf.length; i++) {
                if ((cbuf[i] == 10 || cbuf[i] == 13) && i < cbuf.length) {
                    if (i + 1 < cbuf.length && cbuf[i] == 13 && cbuf[i + 1] == 10) {
                        // window 
                        sb.append(cbuf[i + 1]);
                        appendTab(sb, num);
                        i++;
                    } else {
                        // UINX or OSX 
                        appendTab(sb, num);
                    }
                } else {
                    sb.append(cbuf[i]);
                }
            }
        }
        return sb.toString();
    }

    protected void appendTab(StringBuilder sb, int num) {
        for (int i = 0; i < num; i++) {
            sb.append("\t");
        }
    }

    public String processTemplate(String prefix, Object ctx, VariableResolverFactory factory) {
        Map p = (Map) MVEL.eval(contents, ctx, factory);
        String base = (String) factory.getVariableResolver("$base").getValue();
        String theme = (String) factory.getVariableResolver("theme").getValue();
        String ht = (String) factory.getVariableResolver("ht").getValue();
        String ftId = (String) MVEL.eval("tml", ctx, factory);
        String htId = theme + "." + ht + "." + prefix + "_" + ftId;
        CCMvelTemplate tml = new CCMvelTemplate(base, htId);
        String tmpl = String.valueOf(TemplateRuntime.execute(tml.getTemplate(), ctx, factory));
        return proc_pretty(p.get("$tab"), tmpl);
    }

    public void eachTemplate(String prefix, TemplateOutputStream appender, Object ctx, VariableResolverFactory factory) {
        ICCList qf = (ICCList) factory.getVariableResolver("$fields").getValue();
        qf.stream().forEach((o) -> {
            ICCMap fld = (ICCMap) o;
            fld.put("placeholder", fld.asString("placeholder"));
            fld.put("description", fld.asString("description"));
            fld.put("id", fld.asString("alias", fld.asString("id")));
            factory.createVariable("$meta", fld);
            appender.append(processTemplate(prefix, ctx, factory));
            appender.append("\r\n");
        });
    }

}