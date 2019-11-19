package com.example.skty.springboot.datePrepar;

import java.util.List;
import java.util.Map;

/**
 * 表结构模拟
 */
public class TableImitate {
    private String tableName;//表名
    private List<?> cloums;//所有列数据
    private List<Number> indexs;//主键索引

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<?> getCloums() {
        return cloums;
    }

    public void setCloums(List<?> cloums) {
        this.cloums = cloums;
    }

    public List<Number> getIndexs() {
        return indexs;
    }

    public void setIndexs(List<Number> indexs) {
        this.indexs = indexs;
    }
}
