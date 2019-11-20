package com.example.skty.springboot.datePrepar;

import java.util.HashMap;
import java.util.Map;

/**
 * 模拟数据库类（仅用于当前没有数据库测试用）
 */
public class  DateBaseImitate <K extends  Number> {

    /**
     * 所有的表
     */
    private Map<String,TableImitate<K,Object>> tables;

    public DateBaseImitate() {
        tables=new HashMap<>();
    }

    /**
     * 使用表名创建一个表
     * @param tableName
     * @return
     */
    public boolean createTable(String tableName){
        try {
            tables.put(tableName, TableImitate.createTable(tableName));
        }catch (Exception e){
            return false;
        }
         return true;
    }

    /**
     * 插入数据到指定的表中
     * @return
     */
    public  boolean insertToTable(String tableName,K key,Object data){
        TableImitate<K, Object> table = tables.get(tableName);
        if (table == null) {
            throw new IllegalArgumentException("表不存在,无法插入数据");
        }
        return   table.insertDate(key,data);
    }

}
