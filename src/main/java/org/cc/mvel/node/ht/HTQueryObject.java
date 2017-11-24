package org.cc.mvel.node.ht;

import org.cc.IAProxyClass;
import org.cc.ICCMap;
import org.cc.module.CCMFields;
import org.cc.util.CCJSON;
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
        CCMFields fields = (CCMFields) factory.getVariableResolver("$flds").getValue();
        String cid =  strFactory("cid",factory,"$qf");
        fields.list().stream().forEach((o) -> {
            ICCMap fld = CCJSON.data((ICCMap)o, cid);
            fld.put("placeholder", fld.asString("placeholder","請輸入"+fld.asString("label")));
            fld.put("description", fld.asString("description"));
            fld.put("must", fld.asBool("must"));
            System.out.println(fld);
            appender.append(cell("fld", fld, factory));
            appender.append("\r\n");
        });
        return next != null ? next.eval(runtime, appender, ctx, factory) : null;
    }



}
