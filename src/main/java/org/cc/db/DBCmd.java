package org.cc.db;


import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Stack;
import org.cc.CCList;
import org.cc.CCMap;
import org.cc.ICCMap;
import org.cc.model.CCWorkObject;


/**
 * @author William 整合DBJO ${base}/dp/xxxx.dao 統一使用 支援同文件資料靜態載入 ${ rem ,
 * xxxxxxxxxxxxxxxxxxxxxx} 註解 ${var,dt} 變數模式 ${ op:fld_name:dt} 動態欄位 fld_name op
 * map(fld_name) ${range:fld_name:dt} fld_name beteen map(fld_name_1) and
 * map(fld_name_2) ${like: fld_name} only 字串欄位 會利用 IDB 的 base() 取到 base 位置
 */
public class DBCmd {

    private final static Pattern p = Pattern.compile("\\$\\{([^\\}]+)\\}");
    private final static String dao_src = "dp";

  

    public static ICCMap parser_cmd(CCWorkObject wo) throws Exception {
        ICCMap mq = new CCMap();
        mq.put(CCWorkObject.wo_fields, new CCList());
        Stack<StringBuffer> stack = new Stack<StringBuffer>();
        String cmd = wo.event().asString("$cmd");
        Matcher match = p.matcher(cmd);
        StringBuffer sql = new StringBuffer();
        while (match.find()) {
            String item = match.group(1);
            match.appendReplacement(sql, ""); // 直接清空
            if ("or".equals(item)) {
                stack.push(sql);
                sql = new StringBuffer();
            } else if ("end".equals(item)) {
                if (sql.length() > 0) {
                    String child = sql.toString().replaceFirst("and", "");
                    child = child.replaceAll("and", "or");
                    sql = stack.pop();
                    sql.append(" and (").append(child).append(" )");
                } else {
                    sql = stack.pop();
                }
            } else if (!item.startsWith("rem")) {
                DBCmdItem.process_item(wo.proc().db(), sql, mq, wo.p(), item);
            }
        }
        match.appendTail(sql);
        mq.put(CCWorkObject.wo_sql, sql);
        return mq;
    }

  

}
