package org.cc.mvel.node;

import org.mvel2.MVEL;
import org.mvel2.integration.VariableResolverFactory;
import org.mvel2.templates.TemplateRuntime;
import org.mvel2.templates.res.Node;
import org.mvel2.templates.util.TemplateOutputStream;

/**
 *
 * @author william
 */
public class CCNode extends Node {

    public CCNode(){
        
    }
    
    @Override
    public Object eval(TemplateRuntime runtime, TemplateOutputStream appender, Object ctx, VariableResolverFactory factory) {
        Object o = MVEL.eval(contents, ctx, factory);
        appender.append(asString(o));
        return next != null ? next.eval(runtime, appender, ctx, factory) : null;
    }

    @Override
    public boolean demarcate(Node terminatingNode, char[] template) {
        return false;
    }

    @Override
    public String toString() {
        return "FunctionNode:" + name + "{" + (contents == null ? "" : new String(contents, cStart, cEnd - cStart)) + "} (start=" + begin + ";end=" + end + ")";
    }

    protected String asString(Object eval) {
        return (eval != null) ? eval.toString().trim() : "";
    }
}
