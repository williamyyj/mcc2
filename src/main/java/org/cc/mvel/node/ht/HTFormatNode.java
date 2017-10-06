package org.cc.mvel.node.ht;

import java.util.List;
import org.cc.IAProxyClass;
import org.cc.mvel.node.CCNode;
import org.mvel2.MVEL;
import org.mvel2.integration.VariableResolverFactory;
import org.mvel2.templates.TemplateRuntime;
import org.mvel2.templates.util.TemplateOutputStream;

/**
 * @ht_fmt([fmt,a,b])
 * @author william
 */
@IAProxyClass(id = "ht_format")
public class HTFormatNode extends CCNode {

    @Override
    public Object eval(TemplateRuntime runtime, TemplateOutputStream appender, Object ctx, VariableResolverFactory factory) {
        List list = (List) MVEL.eval(contents, ctx, factory);
        List args = list.subList(1, list.size());
        System.out.println("===== name : "+ this.getName());
        System.out.println("===== params : "+ args);
        return next != null ? next.eval(runtime, appender, ctx, factory) : null;
    }
}
