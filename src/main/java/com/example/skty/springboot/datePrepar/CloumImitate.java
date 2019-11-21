package com.example.skty.springboot.datePrepar;

import java.util.List;

/**
 * 数据行字段模拟
 */
public class CloumImitate {
    /**
     * 一行数据的所有字段
     */
    private List<FieldImitate> fields;

    /**
     * 增加数据表字段
     * @param fieldName 字段名称
     * @param fieldDataType 字段数据类型
     */
    protected  void addField(String fieldName,Class<?> fieldDataType){

    }

    protected List<FieldImitate> getFields() {
        return fields;
    }

    protected void setFields(List<FieldImitate> fields) {
        this.fields = fields;
    }
}
