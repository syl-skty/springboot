package com.example.skty.springboot.datePrepar;

import java.util.*;

/**
 * 表结构模拟
 */
public  class  TableImitate<K extends Number,E> {
    private String tableName;//表名
    private List<E> cloums;//所有列数据
    private Map<K,E> indexes;//索引数据



    public static <K extends  Number,E> TableImitate<K,E> createTable(String tableName){
        if(tableName!=null&&tableName!=""){
            return  new TableImitate<K,E>(tableName);
        }else{
            throw new IllegalArgumentException("表名不能为空");
        }
    }

    /**
     * 创建一个表
     * @param tableName
     */
    private TableImitate(String tableName) {
        this.tableName = tableName;
        cloums= new ArrayList<E>();
        indexes=new HashMap<>();
    }

    /**
     * 往数据表种插入一条数据
     * @return true插入成功 false，插入失败
     */
    public boolean insertDate(K key,E e){
        if (e == null) {
            return  false;
        }
        cloums.add(e);
        indexes.put(key, e);
        return true;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<?> getCloums() {
        return cloums;
    }

    public void setCloums(List<E> cloums) {
        this.cloums = cloums;
    }

    public Map<K, E> getIndexes() {
        return indexes;
    }

    public void setIndexes(Map<K, E> indexes) {
        this.indexes = indexes;
    }
}
