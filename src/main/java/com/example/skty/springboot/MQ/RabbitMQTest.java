package com.example.skty.springboot.MQ;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 测试rabbitMQ
 */
//@Configuration
//@PropertySource("classpath:config.mq/dev_mq.properties")
public class RabbitMQTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    private static final String queueName = "myTest2";
    private static final String exchange = "myExchange";

    //@Scheduled(cron = "0/1 * * * * ?")
    public void send() {
        // rabbitTemplate.convertAndSend("spring-boot-exchange", "spring.example", "我是测试数据");
        rabbitTemplate.convertAndSend(exchange, "spring-boot", "我是topic");
        System.out.println("发送完毕");
    }

    @ConfigurationProperties(prefix = "spring.rabbitmq")
    public RabbitProperties RabbitProperties() {
        return new RabbitProperties();
    }


    @RabbitListener(queues = queueName)
    public void receive(String msg) {
        // Message test = rabbitTemplate.receive("test");
        System.out.println("------我收到消息:" + msg);
    }


    @Bean
    public Queue testQueue() {
        return new Queue(queueName, false, true, true);
    }

    @Bean
    public Exchange testTopicExchange() {
        return new TopicExchange(exchange, false, false);
    }

    @Bean
    public Binding topicBind() {
        return BindingBuilder.bind(testQueue()).to(testTopicExchange()).with("spring-boot").noargs();
    }

}
