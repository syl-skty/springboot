package com.example.skty.springboot.datePrepar;

import com.example.skty.springboot.entity.Person;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 模拟数据库类（仅用于当前没有数据库测试用）
 */
public class  DateBaseImitate  {

    public static void  initDateBase(){
        DateBaseImitate dateBase=new DateBaseImitate();
        TableImitate<Integer, Person> person=new TableImitate<>(dateBase, "person");
        dateBase.addTable(person);
        dateBase.insertDate("person", 1, new Person(1L, "小明", "江西", 18, false));
        dateBase.insertDate("person", 2, new Person(2L, "小花", "湖南", 20, true));
        dateBase.insertDate("person", 3, new Person(3L, "小志", "湖北", 12, false));
        dateBase.insertDate("person", 4, new Person(4L, "小美", "广东", 30, true));
        dateBase.insertDate("person", 5, new Person(5L, "tom", "深圳", 14, false));
    }

    /**
     * 所有的表
     */
    private Map<String,TableImitate<? extends Number,? extends Object>> tables;

    public DateBaseImitate() {
        tables=new HashMap<>();
    }

    /**
     * 根据表名，获取表对象
     * @param tableName
     * @return
     */
    public TableImitate<? extends Number,? extends Object> getTable(String tableName){
        return tables.get(tableName);
    }

    /**
     * 查询对象
     * @param id
     * @param tableName
     * @param c
     * @param <T>
     * @return
     */
    public <T> T QueryById(String tableName,Number id,Class<T> c){
        TableImitate<? extends Number, ? extends  Object> table = getTable(tableName);
        if(table==null){
            throw new  IllegalArgumentException("表不存在");
        }
        return c.cast(table.queryById(id));
    }


    /**
     * 查询对象
     * @param id
     * @param tableName
     * @param c
     * @param <T>
     * @return
     */
    public <T> List<T> QueryAll(String tableName, Number id, Class<T> c){
        TableImitate<? extends Number,? extends Object> table = getTable(tableName);
        if(table==null){
            throw new  IllegalArgumentException("表不存在");
        }
        return Collections.unmodifiableList((List<T>) table.queryAll());
    }

    /**
     * 根据id删除记录
     * @param tableName
     * @param id
     * @return
     */
    public boolean deleteById(String tableName,Number id){
        TableImitate<? extends Number,? extends Object> table = getTable(tableName);
        if(table==null){
            throw new  IllegalArgumentException("表不存在");
        }
      return   table.deleteById(id);
    }


    /**
     * 插入数据
     * @param tableName
     * @param id
     * @return
     */
    public <T extends  Object>   boolean insertDate(String tableName,Number id,T data){
        TableImitate<? extends Number, ? extends Object> table= tables.get(tableName);
        if(table==null){
            throw new  IllegalArgumentException("表不存在");
        }
        return   table.insertToTable(id,data);
    }




    /**
     * 增加一个表到当前的数据库中
     * @param table
     * @return
     */
    public boolean addTable(TableImitate<? extends Number,? extends Object> table){
        if(table==null){
            throw  new  IllegalArgumentException("当前插入的表不存在，无法插入到当前数据库");
        }
        return  tables.put(table.getTableName(),table)!=null;
    }
}
