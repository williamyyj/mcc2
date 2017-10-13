package org.cc.mvel.node.ht;

import org.cc.IAProxyClass;
import org.cc.ICCList;
import org.cc.ICCMap;
import org.mvel2.integration.VariableResolverFactory;
import org.mvel2.templates.TemplateRuntime;
import org.mvel2.templates.util.TemplateOutputStream;

/**
 *
 * @author william
 */
@IAProxyClass(id = "ht_query")
public class HTQueryObject extends HTCellBase {

    public HTQueryObject() {

    }

    @Override
    public Object eval(TemplateRuntime runtime, TemplateOutputStream appender, Object ctx, VariableResolverFactory factory) {
        ICCList qf = (ICCList) factory.getVariableResolver("$fields").getValue();
        qf.stream().forEach((o) -> {
            ICCMap fld = (ICCMap) o;
            fld.put("placeholder", fld.asString("placeholder"));
            fld.put("description", fld.asString("description"));
            fld.put("id", fld.asString("alias", fld.asString("id")));
            fld.put("tml", fld.getOrDefault("tml", ""));
            fld.put("attr", fld.getOrDefault("attr", ""));
            appender.append(cell("fld", fld, factory));
            appender.append("\r\n");
        });
        return next != null ? next.eval(runtime, appender, ctx, factory) : null;
    }

}
