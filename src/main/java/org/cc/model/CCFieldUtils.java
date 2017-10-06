package org.cc.model;


import com.google.common.reflect.ClassPath;
import java.util.HashMap;
import java.util.Map;
import org.cc.IAProxyClass;
import org.cc.ICCMap;
import org.cc.util.CCLogger;

public class CCFieldUtils {

    private static Map<String, Class> _cache;

    private static Object newInstance(String classId) {
        try {
            return Class.forName(classId).newInstance();
        } catch (Exception e) {
            CCLogger.error("Can't newInstance : " + classId);
            return null;
        }
    }

    private static Map<String, Class> cache() {
        if (_cache == null) {
            _cache = new HashMap<String, Class>(32);
            scanPackage(_cache, "org.cc.model.field");
        }
        return _cache;
    }

    private static void scanPackage(Map<String, Class> c, String string) {
        try {
            ClassLoader load = Thread.currentThread().getContextClassLoader();

            ClassPath classpath = ClassPath.from(load);
            classpath.getTopLevelClasses("org.cc.model.field").stream().map((classInfo) -> classInfo.load()).forEach((cls) -> {
                IAProxyClass a = (IAProxyClass) cls.getAnnotation(IAProxyClass.class);
                if (a != null) {
                    c.put(a.id(), cls);
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static ICCField newField(String classId) throws Exception {
        Class cls = cache().get("field." + classId);
        cls = (cls == null) ? cache().get("field.obj") : cls;
        return (ICCField) cls.newInstance();
    }

    public static ICCField newInstance(ICCMap cm) throws Exception {
        ICCField fld = newField(cm.asString("dt"));
        if (fld != null) {
            fld.__init__(cm);
        }
        return fld;
    }

}
