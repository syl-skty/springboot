package com.example.skty.springboot.configurations.value;

import com.example.skty.springboot.annotation.LoadProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.StringValueResolver;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.IOException;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * @author Administrator
 */
public class MyRequestMappingHandlerMapping extends RequestMappingHandlerMapping {
    private boolean useSuffixPatternMatch = true;
    private boolean useRegisteredSuffixPatternMatch = false;
    private boolean useTrailingSlashMatch = true;
    private final String defaultPropertiesPath = "classpath:mapping/mapping-config.properties";
    //系统级别的无url映射地址的Contrller
    private static final Class[] SYS_CONTROLLER_CLASS = {BasicErrorController.class};
    @Nullable
    private StringValueResolver embeddedValueResolver;

    /**
     * 存放当前的配置文件，用于缓存，读入的配置文件将直接放到map中，之后将不需要重复读取文件
     */
    private ConcurrentHashMap<String, Properties> configPropertiesMap = new ConcurrentHashMap<>();

    private RequestMappingInfo.BuilderConfiguration config = new RequestMappingInfo.BuilderConfiguration();


    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        RequestMappingInfo info = createRequestMappingInfo(method);
        if (info != null) {
            RequestMappingInfo typeInfo = createRequestMappingInfo(handlerType);
            if (typeInfo != null) {
                info = typeInfo.combine(info);
            }
            String prefix = getPathPrefix(handlerType);
            if (prefix != null) {
                info = RequestMappingInfo.paths(prefix).build().combine(info);
            }
        }
        return info;
    }

    private RequestMappingInfo createRequestMappingInfo(AnnotatedElement element) {
        RequestMapping requestMapping = AnnotatedElementUtils.findMergedAnnotation(element, RequestMapping.class);
        if (requestMapping != null) {
            String[] path = requestMapping.path();
            boolean isClass = element instanceof Class;
            //没有写参数，就使用配置文件中的
            if (path.length == 0) {
                path = isClass ? getUrlMappingFromProperties((Class<?>) element) : getUrlMappingFromProperties((Method) element);
            }
            RequestCondition<?> condition = isClass ? getCustomTypeCondition((Class<?>) element) : getCustomMethodCondition((Method) element);
            return createRequestMappingInfo(requestMapping, condition, path);
        }
        return null;
    }


    /**
     * 构建map对象
     *
     * @param requestMapping
     * @param customCondition
     * @param truePath        真实的地址映射值，可以有多个
     * @return
     */
    protected RequestMappingInfo createRequestMappingInfo(RequestMapping requestMapping, RequestCondition<?> customCondition, String[] truePath) {
        RequestMappingInfo.Builder builder = RequestMappingInfo
                .paths(resolveEmbeddedValuesInPatterns(truePath))
                .methods(requestMapping.method())
                .params(requestMapping.params())
                .headers(requestMapping.headers())
                .consumes(requestMapping.consumes())
                .produces(requestMapping.produces())
                .mappingName(requestMapping.name());
        if (customCondition != null) {
            builder.customCondition(customCondition);
        }
        return builder.options(this.config).build();
    }

    @Override
    public void afterPropertiesSet() {
        this.config = new RequestMappingInfo.BuilderConfiguration();
        this.config.setUrlPathHelper(getUrlPathHelper());
        this.config.setPathMatcher(getPathMatcher());
        this.config.setSuffixPatternMatch(this.useSuffixPatternMatch);
        this.config.setTrailingSlashMatch(this.useTrailingSlashMatch);
        this.config.setRegisteredSuffixPatternMatch(this.useRegisteredSuffixPatternMatch);
        this.config.setContentNegotiationManager(getContentNegotiationManager());

        super.afterPropertiesSet();
    }

    String getPathPrefix(Class<?> handlerType) {
        for (Map.Entry<String, Predicate<Class<?>>> entry : super.getPathPrefixes().entrySet()) {
            if (entry.getValue().test(handlerType)) {
                String prefix = entry.getKey();
                if (this.embeddedValueResolver != null) {
                    prefix = this.embeddedValueResolver.resolveStringValue(prefix);
                }
                return prefix;
            }
        }
        return null;
    }


    /**
     * 分析当前的元素，从配置文件中读取当前方法和类对应的url映射
     *
     * @param element 当前处理的对象，controller类
     * @return
     */
    private String[] getUrlMappingFromProperties(Class<?> element) {
        if (isSystemController(element)) {//判断当前类是否为系统保留的空参类
            return new String[0];
        }
        LoadProperties annotation = AnnotationUtils.getAnnotation(element, LoadProperties.class);
        String configFilePath = defaultPropertiesPath;
        String keyPath = null;
        if (annotation != null) {
            configFilePath = annotation.path().trim();
            keyPath = Optional.of(annotation.prefix())
                    .filter(StringUtils::hasText)
                    .map(pre -> {
                        return pre + "." + element.getSimpleName();
                    })
                    .orElseGet(() -> annotation.prefix() + "." + element.getSimpleName());
        } else {
            keyPath = element.getName();
        }
        return readFromProperties(configFilePath, keyPath);
    }


    /**
     * 分析当前的元素，从配置文件中读取当前方法和类对应的url映射
     *
     * @param element 当前处理的对象，controller类中的方法
     * @return
     */
    private String[] getUrlMappingFromProperties(Method element) {
        //获取当前的的类对象
        Class<?> controllerClass = element.getDeclaringClass();
        if (isSystemController(controllerClass)) {//判断当前类是否为系统保留的空参类
            return new String[0];
        }
        //获取当前所在Controller的配置文件注解
        LoadProperties annotation = AnnotationUtils.getAnnotation(controllerClass, LoadProperties.class);
        String configFilePath = defaultPropertiesPath;
        String keyPath = null;
        //手动加了注解，使用注解数据，否则使用默认配置
        if (annotation != null) {
            configFilePath = annotation.path().trim();
            //获取在配置文件中的映射,前缀优先使用填写的路径名，否则使用默认的路径名（当前类加方法名的全路径）
            keyPath = Optional.of(annotation.prefix()).filter(StringUtils::hasText).orElse(controllerClass.getName()) + "." + element.getName();
        } else {
            keyPath = controllerClass.getName() + "." + element.getName();
        }
        return readFromProperties(configFilePath, keyPath);
    }


    /**
     * 判断当前是否为系统级别的Controller,有些系统级别的Controller也是注解上没有写值得
     *
     * @param element
     * @return
     */
    private boolean isSystemController(Class<?> element) {
        return Arrays.asList(SYS_CONTROLLER_CLASS).contains(element);
    }


    /**
     * 从配置文件中读取对应的值，支持数组值
     *
     * @param filePath 配置文件路径
     * @param keyName  配置文件中的key前缀，会去匹配所有与之匹配的值
     * @return
     */
    private String[] readFromProperties(String filePath, String keyName) {
        String[] pathArr = null;
        //构建匹配字符正则
        Pattern keyPattern = Pattern.compile("^" + escapeRegexStr(keyName) + "(\\[\\d+])?$", Pattern.CASE_INSENSITIVE);
        try {
            //取一次缓存
            Properties properties = configPropertiesMap.get(filePath);
            if (properties == null) {
                properties = PropertiesLoaderUtils.loadProperties(new FileSystemResource(ResourceUtils.getFile(filePath)));
                //放入缓存
                configPropertiesMap.put(filePath, properties);
            }
            List<String> pathList = new ArrayList<>();
                properties.forEach((k, v) -> {
                    String kStr = StringUtils.trimWhitespace(k.toString());
                    //匹配上就把它放入到列表中
                    if (keyPattern.matcher(kStr).matches()) {
                        pathList.add(v.toString().trim());
                    }
                });
            pathArr = pathList.toArray(new String[0]);
        } catch (IOException e) {
            throw new IllegalArgumentException("当前路径映射配置文件不存在,路径名->" + filePath);
        }
        return pathArr;
    }

    /**
     * 将字符中的所有跟正则相关的字符进行转义
     *
     * @param regx
     * @return
     */
    private String escapeRegexStr(String regx) {
        if (StringUtils.hasText(regx)) {
            String[] fbsArr = {"\\", "$", "(", ")", "*", "+", ".", "[", "]", "?", "^", "{", "}", "|"};
            for (String s : fbsArr) {
                if (regx.contains(s)) {
                    regx = regx.replace(s, "\\" + s);
                }
            }
        }
        return regx;
    }

}
