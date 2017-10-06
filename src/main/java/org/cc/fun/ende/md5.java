package org.cc.fun.ende;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.function.Function;
import org.cc.util.CCEnde;
import org.cc.util.CCLogger;

/**
 *
 * @author william
 */
public class md5 implements Function<byte[], String> {

    @Override
    public String apply(byte[] buf) {
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(buf);
            return CCEnde.toHexString(algorithm.digest());
        } catch (NoSuchAlgorithmException ex) {
            CCLogger.error("fail md5", ex);
        }
        return "";
    }
}
