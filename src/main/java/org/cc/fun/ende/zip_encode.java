package org.cc.fun.ende;

import com.google.common.io.BaseEncoding;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.function.Function;
import java.util.zip.DeflaterOutputStream;
import org.cc.util.CCLogger;

/**
 *
 * @author william
 */
public class zip_encode implements Function<String, String> {

    @Override
    public String apply(String src) {
        DeflaterOutputStream zOut = null;
        try {
            byte[] data = src.getBytes("UTF-8");
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            zOut = new DeflaterOutputStream(out);
            zOut.write(data);
            zOut.finish();
            return BaseEncoding.base64Url().encode(out.toByteArray());
        } catch (IOException ex) {
            CCLogger.error("fail zipencode", ex);
        } finally {
            if (zOut != null) {
                try {
                    zOut.close();
                } catch (IOException ex) {
                    CCLogger.error("Can't close zipencode", ex);
                }
            }
        }
        return null;
    }
}
