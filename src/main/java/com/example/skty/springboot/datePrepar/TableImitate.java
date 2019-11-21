package com.example.skty.springboot.datePrepar;

import java.util.*;

/**
 * 表结构模拟
 */
public  class  TableImitate<K extends Number> {
    private String tableName;//表名
    private DateBaseImitate dataBase;//所属数据库
    private Map<K,Map<String,Object>> data;//表数据



    /**
     * 创建一个表
     * @param tableName
     */
    public TableImitate(DateBaseImitate dataBase,String tableName) {
        if (dataBase == null) {
            throw new IllegalArgumentException("数据库不存在");
        }
        if(tableName==null||tableName==""){
            throw  new IllegalArgumentException("表名不合法");
        }
        this.dataBase=dataBase;
        this.tableName = tableName;

    }

    /**
     * 根据id删除表中的元素
     * @return
     */
    synchronized
    public boolean deleteById(Number id){
        if(id==null){
            return false;
        }
        E e = queryById(id);
        if (e != null) {
            e=null;
            indexes.remove(id);
        }
        return true;
    }

    /**
     * 查询所有数据
     * @return
     */
    public List<E> queryAll(){
        return new ArrayList<E>(indexes.values());
    }


    /**
     * 根据id查询当前表中的数据
     * @param id
     * @return
     */
    public E queryById(Number id){
        return  indexes.get(id);
    }

    /**
     * 插入数据到表中,key有就会更新，没有就会插入
     * @return
     */
    synchronized
    public   boolean insertToTable(Number key,Object data){
        return indexes.put((K) key,(E) data) != null && cloums.add((E)data);
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
