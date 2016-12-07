package org.cc.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 *
 * @author william
 */
public class CCFunc {
    
   private static ConcurrentHashMap<String, Function> cache;

    private static ConcurrentHashMap<String, Function> cache() {
        if (cache == null) {
            cache = new ConcurrentHashMap<String, Function>();
        }
        return cache;
    }

    private static final String prefix = "org.cc.fun";

    private static String classId(String id) {
        if (id != null && !id.startsWith(prefix)) {
            return prefix + "." + id;
        } else {
            return id;
        }
    }

    private static Function load(String classId) throws Exception {
        try {
            Class<?> cls = Class.forName(classId);
            CCLogger.debug("Load function  " + classId);
            Function fun = (Function) cls.newInstance();
            cache().put(classId, fun);
            return fun;
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            CCLogger.error("Can't find classId : " + classId);
            return null;
        }
    }

    public static Object exec(String id, Object p) throws Exception {
        String classId = classId(id);
        Function fun = cache().get(classId);
        fun = (fun == null) ? fun = load(classId) : fun;
        if (fun != null) {
            return fun.apply(p);
        } else {
            return null;
        }
    }

    public static <T> T exec(Class<T> c, String id, Object p) throws Exception {
        return (T) exec(id, p);
    }

  
}
