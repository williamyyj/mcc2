package org.cc.model;

import java.util.HashMap;
import java.util.Map;
import org.cc.ICCList;
import org.cc.ICCMap;
import org.cc.util.CCJSON;

/**
 * @author william
 */
public class CCMetadata {

    private String base;

    private String prefix;

    private ICCMap cfg;

    private Map<String, ICCField> _fields;

    public CCMetadata(String base, String prefix, String metaId) {
        super();
        this.base = base;
        this.prefix = prefix;
        _fields = new HashMap<>();
        this.load_metadata(metaId);
    }

    public CCMetadata(String base, String metaId) {
        //  相容  JOMetadata 
        this(base, "/dp/metadata", metaId);
    }

    private void load_metadata(String metaId) {
        System.out.println(base + prefix);
        cfg = CCJSON.load(base + prefix, metaId);
        if (cfg.containsKey("meta")) {
            ICCList meta = cfg.list("meta");
            meta.stream().forEach((Object o) -> {
                try {
                    ICCMap item = (ICCMap) o;
                    ICCField field = CCFieldUtils.newInstance(item);
                    _fields.put(item.asString("id"), field);
                } catch (Exception ex) {
                    ex.printStackTrace(System.out);
                }
            });
        }
    }

    public Map<String, ICCField> fields() {
        return _fields;
    }

    public ICCMap cfg() {
        return cfg;
    }

    public ICCMap event(String eventId) {
        return cfg.map(eventId);
    }

}
