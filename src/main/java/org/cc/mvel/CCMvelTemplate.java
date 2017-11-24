package org.cc.mvel;

import java.io.File;
import java.io.IOException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.cc.IAProxyClass;
import org.cc.data.CCDataUtils;
import org.cc.reflection.CCPackage;
import org.cc.util.CCLogger;
import org.mvel2.integration.impl.MapVariableResolverFactory;
import org.mvel2.templates.CompiledTemplate;
import org.mvel2.templates.TemplateCompiler;
import org.mvel2.templates.TemplateRuntime;
import org.mvel2.templates.res.Node;

/**
 *
 * @author william
 */
public class CCMvelTemplate implements ICCTemplate {

    protected static Map<String, Class<? extends Node>> _customNode;
    private String prefix = "mvel";
    private char[] content;
    private String base;
    protected CompiledTemplate template;

    public static Map<String, Class<? extends Node>> getCustomNode() {
        if (_customNode == null) {
            try {
                _customNode = new HashMap<>();
                List<Class> clss = CCPackage.getClasses("org.cc.mvel.node");
                clss.stream().forEach(cls -> {
                    IAProxyClass a = (IAProxyClass) cls.getAnnotation(IAProxyClass.class);
                    if (a != null) {
                        _customNode.put(a.id(), cls);
                    }
                });
            } catch (ClassNotFoundException | IOException ex) {
                CCLogger.error(ex);
            }
        }
        return _customNode;
    }

    public CCMvelTemplate(String base, String fid, String suffix) {
        try {
            this.base = base;
            String fileName = fid.replace(".", "/") + "." + suffix;
            File f = new File(base + "/" + prefix, fileName);
            content = CCDataUtils.loadClob(new File(base + "/" + prefix, fileName), "UTF-8");
            this.compiler();
        } catch (Exception ex) {
            ex.printStackTrace();
            CCLogger.info("Can't load " + fid);
        }
    }

    public CCMvelTemplate(String base, char[] content) {
        try {
            this.base = base;
            this.content = content;
            this.compiler();
        } catch (Exception ex) {

        }
    }

    public CCMvelTemplate(String base, String fid) {
        this(base, fid, "htm");
    }

    @Override
    public String getContent() {
        return new String(content);
    }

    @Override
    public Object execute(Map<String, Object> m) {
        m.put("$base", base);
        return new TemplateRuntime(template.getTemplate(), null, template.getRoot(), base + "/" + prefix).execute(new StringBuilder(), null, new MapVariableResolverFactory(m));
    }

    public void compiler(Map<String, Class<? extends Node>> nodes) {
        template = TemplateCompiler.compileTemplate(content, nodes);
    }

    private void compiler() {
        template = TemplateCompiler.compileTemplate(content, CCMvelTemplate.getCustomNode());
    }

    public CompiledTemplate getTemplate() {
        return this.template;
    }

}
