package com.example.skty.springboot.eventlistenner;

import org.springframework.boot.autoconfigure.jdbc.DataSourceSchemaCreatedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 自定义监听spring组件启动器，监听事件为数据源加载事件，通过这个监听器可以实现在数据源加载完成后指定执行一些sql文件
 */
@Component
public class DataSourceSchemaCreatedEventListenner implements ApplicationListener<DataSourceSchemaCreatedEvent> {
    /**
     * Handle an application event.
     *
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(DataSourceSchemaCreatedEvent event) {

    }
}
