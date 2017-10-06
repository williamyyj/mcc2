package org.cc.module;

import org.cc.ICCList;
import org.cc.ICCMap;
import org.cc.util.CCJSON;
import org.cc.util.CCPath;

/**
 *
 * @author william
 */
public class CCEvent {

    private CCModule cm;

    private String eventId;

    private ICCMap cfg;

    private ICCList dFields;

    private ICCList qFields;

    public CCEvent(CCModule cm, String eventId) {
        this.cm = cm;
        this.eventId = eventId;
        cfg = CCJSON.data(cm.cfg(), CCPath.map(cm.cfg(), "$event:" + eventId));
        qFields = CCPath.list(cm.cfg(), "$cell:" + cfg.asString("$query") + ":$fields");
        dFields = CCPath.list(cm.cfg(), "$cell:" + cfg.asString("$grid") + ":$fields");
    }

    public String cmd() {
        StringBuilder sb = new StringBuilder();
        sb.append(" select");
        dFields.forEach((o) -> {
            System.out.println(o);
            ICCMap fld = (ICCMap) o;
            sb.append(" ").append(fld.asString("id")).append(",");
        });
        sb.setLength(sb.length() - 1); // skip , 
        return sb.toString();
    }

    public String cond() {
        StringBuilder sb = new StringBuilder();
        sb.append(" where 1=1 \r\n");
        qFields.forEach((o) -> {
            ICCMap fld = (ICCMap) o;
            if (fld.containsKey("qf")) {
                sb.append("${").append(fld.asString("qf"))
                   .append(",").append(fld.asString("id"))
                   .append(",").append(fld.asString("dt"));
                sb.append("}");
            }
        });
        sb.setLength(sb.length() - 1); // skip , 
        return sb.toString();
    }

}
