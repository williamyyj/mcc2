package org.cc.fun.ende;

import com.google.common.io.BaseEncoding;
import com.ning.compress.lzf.LZFDecoder;
import com.ning.compress.lzf.LZFException;
import java.io.UnsupportedEncodingException;
import java.util.function.Function;
import org.cc.util.CCLogger;


/**
 *
 * @author william
 */
public class lzf_decode implements Function<String, String> {

    @Override
    public String apply(String text) {
        if (text != null && text.length() > 0) {
            try {
                byte[] ret = BaseEncoding.base64Url().decode(text);
                return new String(LZFDecoder.decode(ret), "UTF-8");
            } catch (LZFException | UnsupportedEncodingException ex) {
                CCLogger.error("fail lzfdecode",ex);
            }
        }
        return "";
    }

}
