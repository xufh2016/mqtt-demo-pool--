package com.coolfish.demo.mqtt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @className: MqttConfig
 * @description: TODO 类描述
 * @author: xufh
 * @date: 2023/12/13
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "mqtt")
public class MqttConfig {
    /**
     * MQTT host 地址
     */
    private String host;

    /**
     * 客户端Id
     */
    private String clientId;

    /**
     * 登录用户(可选)
     */
    private String username;

    /**
     * 登录密码(可选)
     */
    private String password;

    /**
     * Mqtt Pool Config
     */
    private MqttPoolConfig poolConfig;
}

