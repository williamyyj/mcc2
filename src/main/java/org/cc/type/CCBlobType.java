/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;


public class CCBlobType extends CCBaseType<byte[]> {

    public String dt() {
        return dt_blob;
    }

    public byte[] value(Object o, byte[] dv) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public byte[] getRS(ResultSet rs, String name) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setPS(PreparedStatement ps, int idx, Object value) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Class<?> nativeClass() {
        try {
            return Class.forName("[B");
        } catch (ClassNotFoundException ex) {
            return null;
        } 
    }

    public int dt_sql() {
        return Types.BLOB;
    }
    
}
