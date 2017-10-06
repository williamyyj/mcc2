package org.cc.mvel;


import java.util.Map;

/**
 *
 * @author william
 */
public interface ICCTemplate {
    
    public String getContent();
    
    public Object execute(Map<String,Object> m);
}
