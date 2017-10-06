package org.cc.mvel.node.ht;

import java.util.Map;
import org.cc.IAProxyClass;
import org.mvel2.MVEL;
import org.mvel2.integration.VariableResolverFactory;
import org.mvel2.templates.TemplateRuntime;
import org.mvel2.templates.util.TemplateOutputStream;

/**
 *
 * @author william
 */
@IAProxyClass(id = "ht_cell")
public class HTCellObject extends HTCellBase {

    public HTCellObject() {

    }

    @Override
    public Object eval(TemplateRuntime runtime, TemplateOutputStream appender, Object ctx, VariableResolverFactory factory) {
         Map p = (Map) MVEL.eval(contents, ctx, factory);
        appender.append(processTemplate("cell",p,factory));
        return next != null ? next.eval(runtime, appender, ctx, factory) : null;
    }

}
