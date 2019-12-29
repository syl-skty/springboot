package com.example.skty.springboot.configurations.value;

import com.example.skty.springboot.annotation.LoadProperties;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.ResourceUtils;
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
    private String defaultPropertiesPath = "classpath:";
    @Nullable
    private StringValueResolver embeddedValueResolver;

    /**
     * 存放当前的配置文件，用于缓存，读入的配置文件将直接放到map中，之后将不需要重复读取文件
     */
    private ConcurrentHashMap<String, Properties> configPropertiesMap = new ConcurrentHashMap<>();

    private RequestMappingInfo.BuilderConfiguration config = new RequestMappingInfo.BuilderConfiguration();


    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        System.out.println("我的执行" + handlerType.getName());
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
    protected RequestMappingInfo createRequestMappingInfo(RequestMapping requestMapping, RequestCondition<?> customCondition) {
        RequestMappingInfo.Builder builder = RequestMappingInfo
                .paths(resolveEmbeddedValuesInPatterns(requestMapping.path()))
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

        return null;
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
        //获取当前所在Controller的配置文件注解
        LoadProperties annotation = AnnotationUtils.getAnnotation(controllerClass, LoadProperties.class);
        if (annotation != null) {
            String path = annotation.path();
            //获取在配置文件中的映射,前缀优先使用填写的路径名，否则使用默认的路径名（当前类的全路径）
            String fullPath = Optional.of(annotation.prefix()).orElse(controllerClass.getName()) + element.getName();

        }
        return null;
    }


    /**
     * 从配置文件中读取对应的值，支持数组值
     *
     * @param filePath 配置文件路径
     * @param keyName  配置文件中的key前缀，会去匹配所有与之匹配的值
     * @return
     */
    private String[] readFromProperties(String filePath, String keyName) {
        try {
            Properties properties = PropertiesLoaderUtils.loadProperties(new FileSystemResource(ResourceUtils.getFile(filePath)));
            if (properties != null) {
                List<String> pathArr = new ArrayList();
                properties.forEach((k, v) -> {

                });
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("当前路径映射配置文件不存在,路径名->" + filePath);
        }

        return null;
    }

    private boolean checkPathArrMatch(String path, String beCheck) {
        Pattern.matches("test(\\[\\d+\\])?", "");
        return false;
    }


}
