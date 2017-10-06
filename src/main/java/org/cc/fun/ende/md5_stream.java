package org.cc.fun.ende;

import java.io.InputStream;
import java.security.MessageDigest;
import java.util.function.Function;
import org.cc.util.CCEnde;
import org.cc.util.CCLogger;

/**
 *
 * @author william
 */
public class md5_stream implements Function<InputStream, String> {

    @Override
    public String apply(InputStream is) {
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            return CCEnde.digest(algorithm, is);
        } catch (Exception ex) {
            CCLogger.error("fail md5_stream", ex);
        }
        return "";
    }

}
