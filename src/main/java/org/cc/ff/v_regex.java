package org.cc.ff;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.cc.CCProcObject;
import org.cc.ICCList;
import org.cc.ICCMap;
import org.cc.util.CCPath;

/**
 *
 * @author william
 */
public class v_regex extends CCFFBase<Boolean> {

    protected Pattern p;

    public v_regex() {

    }

    @Override
    public void __init_proc(CCProcObject proc) {
        p = Pattern.compile(cfg.asString("$cmd") );
    }

    @Override
    protected Boolean apply(ICCMap row, String id, Object o) {
        try {
            int size = cfg.asInt("size");
            boolean nn = cfg.asBool("$nn", false); // 空值不檢查
            String code = cfg.asString("code");
            ICCList msg = cfg.list("msg");
            String fv = (o != null) ? o.toString().trim() : "";
            if (!nn && fv.length() > 0) {
                if (fv.length() > size) {
                    // 長度異常
                    CCPath.set(row, "$fv:" + id, code + ":" + msg.asString(0));
                    return false;
                }
                Matcher m = p.matcher(fv);
                if (!(m.matches())) {
                    // 格式錯誤
                    CCPath.set(row, "$fv:" + id, code + ":" + msg.asString(1));
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
