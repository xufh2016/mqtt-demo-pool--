package com.coolfish.demo.mqtt;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @className: MqttConnection
 * @description: TODO 类描述
 * @author: xufh
 * @date: 2023/12/13
 */
public class MqttConnection {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private MqttClient mqttClient;

    public MqttConnection(MqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }

    /**
     * 隶属于的连接池
     */
    private GenericObjectPool<MqttConnection> belongedPool;


    /**
     * 推送方法消息
     */
    public void publish(String topic, String message) throws Exception {
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setPayload(message.getBytes());
        mqttClient.publish(topic, mqttMessage);
        System.out.println("对象：" + mqttClient + " " + "发送消息：" + message);
    }


    /**
     * 销毁连接
     */
    public void destroy() {
        try {
            if (this.mqttClient.isConnected()) {
                this.mqttClient.disconnect();
            }
            this.mqttClient.close();
        } catch (Exception e) {
            logger.error("MqttConnection destroy ERROR ; errorMsg={}", e.getMessage());
        }
    }

    /**
     * 换回连接池
     */
    public void close() {
        if (belongedPool != null) {
            this.belongedPool.returnObject(this);
        }
    }


    public MqttClient getMqttClient() {
        return mqttClient;
    }

    public void setMqttClient(MqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }

    public GenericObjectPool<MqttConnection> getBelongedPool() {
        return belongedPool;
    }

    public void setBelongedPool(GenericObjectPool<MqttConnection> belongedPool) {
        this.belongedPool = belongedPool;
    }
}