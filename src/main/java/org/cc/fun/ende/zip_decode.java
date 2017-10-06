package org.cc.fun.ende;

import com.google.common.io.BaseEncoding;
import java.io.ByteArrayInputStream;
import java.util.function.Function;
import java.util.zip.InflaterInputStream;
import org.cc.util.CCEnde;
import org.cc.util.CCLogger;

/**
 *
 * @author william
 */
public class zip_decode implements Function<String, String> {

    @Override
    public String apply(String text) {
        if (text != null && text.length() > 0) {
            try {
                byte[] buf = BaseEncoding.base64Url().decode(text);
                byte[] data = CCEnde.loadData(new InflaterInputStream(new ByteArrayInputStream(buf)));
                return new String(data, "UTF-8");
            } catch (Exception ex) {
                CCLogger.error("fail zipdecode", ex);
            }
        }
        return null;
    }

}
