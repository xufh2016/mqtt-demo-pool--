package com.coolfish.demo.mqtt;

import cn.hutool.core.date.DateUtil;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @className: PushCallback
 * @description: TODO 类描述
 * @author: xufh
 * @date: 2023/12/13
 */
public class PublishCallback implements MqttCallback {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void connectionLost(Throwable cause) {
        // 连接丢失后进行重连
        System.out.println("连接断开，可以做重连");
        logger.info("掉线时间:{}", DateUtil.now());
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("deliveryComplete---------" + token.isComplete());
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println("接收消息主题 : " + topic);
        System.out.println("接收消息Qos : " + message.getQos());
        System.out.println("接收消息内容 : " + new String(message.getPayload()));
    }
}
