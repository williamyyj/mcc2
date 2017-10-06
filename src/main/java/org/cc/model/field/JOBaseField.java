package org.cc.model.field;

import org.cc.ICCMap;
import org.cc.ICCType;
import org.cc.CCMap;
import org.cc.model.ICCField;

public class JOBaseField<E> extends CCMap implements ICCField<E> {

    protected ICCType<E> type;

    @Override
    public void __init__(ICCMap cfg) throws Exception {
        super.putAll(cfg);
    }

    /**
     * 資料庫欄位名
     *
     * @return
     */
    @Override
    public String name() {
        return asString("name", asString("id"));
    }

    /**
     * 系統識別值 （唯一)
     *
     * @return
     */
    @Override
    public String id() {
        return asString("id");
    }

    @Override
    public String dt() {
        return asString("dt");
    }

    @Override
    public String label() {
        return asString("label");
    }

    @Override
    public int dtSize() {
        return asInt("size", 0);
    }

    public String label(String lang) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * DB : P -> pk , F-> FK , I -> index , N -> not null
     *
     * @return
     */
    @Override
    public String ct() {
        return asString("ct", null);
    }

    /**
     *
     *
     * @return
     */
    @Override
    public String ft() {
        return asString("ft", null);
    }

    @Override
    public ICCType<E> type() {
        return this.type;
    }

    @Override
    public String alias() {
        return asString("alias", null);
    }

    @Override
    public String fmt() {
        return asString("fmt", null);
    }

    @Override
    public String notes() {
        return asString("notes", null);
    }

    @Override
    public void setFieldValue(ICCMap row, Object value) {
        if (row != null) {
            row.put(name(), type().value(value));
        }
    }

    @Override
    public ICCMap cfg() {
        return this;
    }

}
