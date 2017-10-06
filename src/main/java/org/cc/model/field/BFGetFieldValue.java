package org.cc.model.field;

import java.util.function.BiFunction;
import org.cc.ICCMap;
import org.cc.model.ICCField;

/**
 *
 * @author william
 * @param <E>
 */
public class BFGetFieldValue<E> implements BiFunction<ICCField<E>,ICCMap,Object> {

    @Override
    public E apply(ICCField<E> fld, ICCMap row) {
        return null;
    }
    
}
