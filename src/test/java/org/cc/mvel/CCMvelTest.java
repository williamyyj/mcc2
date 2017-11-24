/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.mvel;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.mvel2.MVEL;
import org.mvel2.ParserContext;
import org.mvel2.ast.ASTNode;
import org.mvel2.integration.Interceptor;
import org.mvel2.integration.VariableResolverFactory;

/**
 *
 * @author william
 */
public class CCMvelTest {

    @Test
    public void test_interceptors() {
        ParserContext context = new ParserContext();
        Map<String, Interceptor> myInterceptors = new HashMap<String, Interceptor>();

        Interceptor myInterceptor = new Interceptor() {
            @Override
            public int doBefore(ASTNode node, VariableResolverFactory factory) {
                System.out.println("BEFORE!");
                return 0;
            }

            @Override
            public int doAfter(Object value, ASTNode node, VariableResolverFactory factory) {
                System.out.println("AFTER!");
                return 0;
            }
        };

        myInterceptors.put("Foo", myInterceptor);
        context.setInterceptors(myInterceptors);
        // Serializable compiledExpression = MVEL.compileExpression(expression, context);
    }

}
