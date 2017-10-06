package org.cc.codebase;

import java.io.File;
import org.cc.CCTest;
import org.junit.Test;

/**
 * @author william
 */
public class CodeBaseTest {


    public void test_Load() {
        CCCodeBase cb = new CCCodeBase(CCTest.prj_base,"cp20171005");
        cb.pack();
        cb.list();
    }
    
    
    public static void main(String[] args){
        String codebase="D:\\will\\maintenance\\baphiq\\codebase\\baphiq\\WEB-INF\\lib";
        String source = "D:\\will\\work\\nb\\mwork\\target\\mwork-1.0-SNAPSHOT\\WEB-INF\\lib";
        File cb = new File(codebase);
        File cp = new File(source);
        File[] list = cp.listFiles();
        for(File item : list){
            String fname = item.getName();
            File base = new File(cb,fname);
            if(!base.exists()){
                System.out.println("\"@{前台}/WEB-INF/lib/"+fname+"\"");
            }
        }
        
    }
}
