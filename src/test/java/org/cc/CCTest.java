package org.cc;

import java.io.File;

/**
 * @author william
 */
public class CCTest {

    public static String root = "";
    public static String base = "";
    public static String prj_base = "";

    static {

        if ("Mac OS X".equals(System.getProperty("os.name"))) {
            root = "/Users/william/Dropbox/resources";
        } else {
            root = "D:\\Users\\william\\Dropbox\\resources\\";
        }
        base = root + "/prj/baphiq";
        prj_base = root + "/project/baphiq";

    }

}
