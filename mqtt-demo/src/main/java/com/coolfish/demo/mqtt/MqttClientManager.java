package com.coolfish.demo.mqtt;


import cn.hutool.core.util.StrUtil;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @className: MqttClientManager
 * @description: 对类的创建之前进行初始化的操作，在afterPropertiesSet()中完成。
 * @author: xufh
 * @date: 2023/12/13
 */
@Component
public class MqttClientManager implements InitializingBean {
    private static Logger logger = LoggerFactory.getLogger(MqttClientManager.class);

    /**
     * mqtt连接配置
     */
    private final MqttConfig mqttConfig;

    private MqttConnectionPool mqttPool;

    public MqttClientManager(MqttConfig mqttConfig) {
        this.mqttConfig = mqttConfig;
    }

    /**
     * 创建连接池
     */
    @Override
    public void afterPropertiesSet() {
        try {
            // 连接池配置
            GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
            this.initPoolConfig(poolConfig);

            // mqtt连接配置
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setUserName(this.mqttConfig.getUsername());
            if (StrUtil.isNotEmpty(mqttConfig.getPassword())) {
                connOpts.setPassword(this.mqttConfig.getPassword().toCharArray());
            }

            // 创建工厂对象
            MqttConnectionFactory connectionFactory = new MqttConnectionFactory(mqttConfig.getHost(), connOpts);

            // 创建连接池
            mqttPool = new MqttConnectionPool(connectionFactory, poolConfig);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void initPoolConfig(GenericObjectPoolConfig poolConfig) {

        MqttPoolConfig mqttConnectionPoolConfig = this.mqttConfig.getPoolConfig();

        if (mqttConnectionPoolConfig.isCustomSet()) {

            // 设置连接池配置信息
            poolConfig.setMinIdle(mqttConnectionPoolConfig.getMinIdle());
            poolConfig.setMaxIdle(mqttConnectionPoolConfig.getMaxIdle());
            poolConfig.setMaxTotal(mqttConnectionPoolConfig.getMaxTotal());
            // TODO 补全

        }
    }

    /**
     * 根据key找到对应连接
     */
    public MqttConnection getConnection() throws Exception {
        return this.mqttPool.borrowObject();
    }

}

