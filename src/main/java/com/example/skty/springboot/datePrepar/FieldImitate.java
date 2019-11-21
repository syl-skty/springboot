package com.example.skty.springboot.datePrepar;

/**
 * 数据库字段模拟
 */
public class FieldImitate {
    private String fieldName;//字段名
    private Class<?> dataType;//字段数据类型

    protected String getFieldName() {
        return fieldName;
    }

    protected void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    protected Class<?> getDataType() {
        return dataType;
    }

    protected void setDataType(Class<?> dataType) {
        this.dataType = dataType;
    }
}
