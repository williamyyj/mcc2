package org.cc.fun.ende;

import com.google.common.io.BaseEncoding;
import com.ning.compress.lzf.LZFEncoder;
import java.io.UnsupportedEncodingException;
import java.util.function.Function;
import org.cc.util.CCLogger;

/**
 *
 * @author william
 */
public class lzf_encode implements Function<String, String> {

    @Override
    public String apply(String text) {
        if (text != null && text.length() > 0) {
            try {
                byte[] ret = LZFEncoder.encode(text.getBytes("UTF-8"));
                return BaseEncoding.base64Url().encode(ret);
            } catch (UnsupportedEncodingException ex) {
                CCLogger.error("fail lzfencode", ex);
            }
        }
        return "";
    }

}
