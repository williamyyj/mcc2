package org.cc.mvel.node;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.cc.IAProxyClass;
import org.cc.mvel.CCMvelTemplate;
import org.mvel2.MVEL;
import org.mvel2.integration.VariableResolverFactory;
import org.mvel2.templates.TemplateError;
import org.mvel2.templates.TemplateRuntime;
import org.mvel2.templates.res.Node;
import org.mvel2.templates.util.TemplateOutputStream;

/**
 *
 * @author william
 */
@IAProxyClass(id = "template")
public class TemplateNode extends Node {

    private CCMvelTemplate template;

    public TemplateNode() {
    }

    @Override
    public Object eval(TemplateRuntime runtime, TemplateOutputStream appender, Object ctx, VariableResolverFactory factory) {
        String fid = MVEL.eval(contents, ctx, factory, String.class);
        String base = (String) factory.getVariableResolver("$base").getValue();
        if(template==null){
            template = new CCMvelTemplate(base,fid);
        }
        appender.append( String.valueOf(TemplateRuntime.execute(template.getTemplate(), ctx, factory)));
        return next != null ? next.eval(runtime, appender, ctx, factory) : null;
    }

    @Override
    public boolean demarcate(Node terminatingNode, char[] template) {
        return false;
    }

    public static String readInFile(TemplateRuntime runtime, String fileName) {
        File file = new File(String.valueOf(runtime.getRelPath().peek()) + "/" + fileName);
        System.out.println("====== chcek mp  " + runtime.getRelPath().peek());
        try {
            FileInputStream instream = new FileInputStream(file);
            BufferedInputStream bufstream = new BufferedInputStream(instream);

            runtime.getRelPath().push(file.getParent());

            byte[] buf = new byte[10];
            int read;
            int i;

            StringBuilder appender = new StringBuilder();

            while ((read = bufstream.read(buf)) != -1) {
                for (i = 0; i < read; i++) {
                    appender.append((char) buf[i]);
                }
            }

            bufstream.close();
            instream.close();

            runtime.getRelPath().pop();

            return appender.toString();

        } catch (FileNotFoundException e) {
            throw new TemplateError("cannot include template '" + fileName + "': file not found.");
        } catch (IOException e) {
            throw new TemplateError("unknown I/O exception while including '" + fileName + "' (stacktrace nested)", e);
        }
    }
}
