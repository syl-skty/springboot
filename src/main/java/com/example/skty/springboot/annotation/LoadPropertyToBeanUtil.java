package com.example.skty.springboot.annotation;

import com.example.skty.springboot.configurations.value.MyRequestMappingHandlerMapping;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Optional;
import java.util.Properties;

/**
 * 加载properties文件到指定的bean中
 */
public class LoadPropertyToBeanUtil {

    /**
     * 将指定的配置文件中的属性与当前配置类中的属性进行绑定
     *
     * @param currentClass 当前要绑定的类对象
     */
    public static void loadProperties(Class<?> currentClass) {
        MyRequestMappingHandlerMapping r = new MyRequestMappingHandlerMapping();
        LoadProperties loadPropertiesAnnotation = AnnotationUtils.getAnnotation(currentClass, LoadProperties.class);
        if (loadPropertiesAnnotation != null) {
            Properties properties = readPropertiesFile(loadPropertiesAnnotation.mappingFilePath());
            try {
                obtainBeanFromProperties(properties, currentClass, loadPropertiesAnnotation.prefix());
            } catch (ReflectiveOperationException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将指定的配置文件中的属性与当前配置类中的属性进行绑定
     *
     * @param currentClass   当前要绑定的类对象
     * @param propertiesPath 要绑定到当前配置类中的配置文件的文件路径，可使用classpath:**这种格式的路径
     * @param prefix         属性绑定的前缀
     */
    public static void loadProperties(Class<?> currentClass, String propertiesPath, String prefix) {
        Properties properties = readPropertiesFile(propertiesPath);
        try {
            obtainBeanFromProperties(properties, currentClass, prefix);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取指定的配置文件，将其转换为properties对象
     *
     * @param filePath 文件路径
     * @return
     */
    private static Properties readPropertiesFile(String filePath) {
        Properties properties = null;
        if (StringUtils.hasText(filePath)) {
            try {
                //读取文件
                properties = PropertiesLoaderUtils.loadProperties(new FileSystemResource(ResourceUtils.getFile(filePath)));
            } catch (IOException e) {
                e.printStackTrace();//配置文件读取失败
            }
        }
        return properties;
    }

    /**
     * 使用properties文件中的属性键值对填充目标类中的属性值
     *
     * @param properties
     * @param targetClass
     */
    private static void obtainBeanFromProperties(Properties properties, Class<?> targetClass, String prefix) throws IllegalAccessException, InstantiationException {
        if (properties != null && targetClass != null) {
            Object targetObj = targetClass.newInstance();
            Field[] fields = targetClass.getDeclaredFields();
            for (Field field : fields) {
                setValueFromPropertiesByType(targetObj, field, properties, prefix);
            }
        }
    }

    /**
     * 根据不同的数据类型从配置文件中取值，并配置进去
     *
     * @param targetObj  目标对象
     * @param field      目标属性字段
     * @param properties 配置文件对象
     */
    private static void setValueFromPropertiesByType(Object targetObj, Field field, Properties properties, String prefix) {
        String fieldName = field.getName();
        Class<?> fieldType = field.getType();
        final String prefixValue = StringUtils.hasText(prefix) ? prefix + "." : "";
        properties.forEach((key, value) -> {
            String propertiesKey = key.toString().toLowerCase();
            if (propertiesKey.equalsIgnoreCase(prefixValue + fieldName)) {
                Optional.ofNullable(value).map(String::valueOf).filter(StringUtils::hasText).ifPresent(setValueStr -> {
                    Object setValue = null;
                    if (fieldType == String.class) {
                        setValue = setValueStr;
                    } else if (fieldType == Integer.class || fieldType == int.class) {
                        setValue = Integer.parseInt(setValueStr);
                    } else if (fieldType == Long.class || fieldType == long.class) {
                        setValue = Long.parseLong(setValueStr);
                    } else if (fieldType == Double.class || fieldType == double.class) {
                        setValue = Double.parseDouble(setValueStr);
                    } else if (fieldType == Float.class || fieldType == float.class) {
                        setValue = Float.parseFloat(setValueStr);
                    } else if (fieldType == Boolean.class || fieldType == boolean.class) {
                        setValue = Boolean.parseBoolean(setValueStr);
                    } else if (fieldType == Character.class || fieldType == char.class) {
                        setValue = setValueStr.charAt(0);
                    }
                    if (setValue != null) {
                        Field modifiers = null;
                        try {
                            field.setAccessible(true);//将属性的修改性设置先打开
                            modifiers = field.getClass().getDeclaredField("modifiers");//获取属性字段中的修饰字段，static和final修饰
                            modifiers.setAccessible(true);//将当前的属性修饰符的可修改性设置为开放
                            modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);//先将当前修饰的final进行删除
                            field.set(targetObj, setValue);//设置属性值
                            modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);//将属性修饰符final重新加上
                        } catch (ReflectiveOperationException e) {
                            e.printStackTrace();
                        } finally {
                            if (modifiers != null) {
                                modifiers.setAccessible(false);
                            }
                            field.setAccessible(false);
                        }
                    }
                });
            }
        });
    }
}
