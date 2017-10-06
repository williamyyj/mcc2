package org.cc.db.cmd;

import java.sql.SQLException;
import org.cc.db.IDB;

/**
 *
 * @author william
 * @param <P>  參數
 * @param <M> 回傳值
 */
public interface IDBCommand<P,M> {
    public M execute(IDB db, String cmd, P p) throws SQLException ;
}
