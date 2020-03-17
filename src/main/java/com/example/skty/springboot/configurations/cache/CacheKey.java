package com.example.skty.springboot.configurations.cache;

import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

/**
 * 缓存使用时需要构建的key对象，通过这个对象来生成对应的缓存key
 */
public class CacheKey {

    /**
     * 标识id,用于某些特殊情况下要与当前资源绑定的情况
     */
    private final String id;

    /**
     * 是否使用id验证
     */
    private final boolean useId;

    /**
     * 需要被缓存结果所执行的方法
     */
    private final Method method;

    /**
     * 方法的参数列表
     */
    private final Object[] params;

    /**
     * 创建缓存的key对象，之后会调用toString方法生成缓存key
     *
     * @param method 被缓存结果的方法
     * @param params 被缓存结果的方法参数
     */
    public CacheKey(Method method, Object... params) {
        this.method = method;
        this.params = params;
        this.id = null;
        this.useId = false;
    }

    /**
     * 创建缓存的key对象
     *
     * @param id     用于标识当前资源的id（如人员id,会话id等）
     * @param method 执行的方法
     * @param params 方法参数
     */
    public CacheKey(String id, Method method, Object[] params) {
        this.id = id;
        this.method = method;
        this.params = params;
        this.useId = true;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof CacheKey) {
            CacheKey c = (CacheKey) other;
            if (c.method.getName().equals(this.method.getName())) {
                return Arrays.deepEquals(this.params, c.params);
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(method);
        result = 31 * result + Arrays.hashCode(params);
        return result;
    }

    @Override
    public String toString() {
        return method.getName() + "(" + StringUtils.arrayToCommaDelimitedString(this.params) + ")" + (useId ?
                "->" + id : "");
    }
}
