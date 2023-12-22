package com.coolfish.demo;

import com.coolfish.demo.mqtt.MqttClientManager;
import com.coolfish.demo.mqtt.MqttUtil;
import com.coolfish.demo.mqtt.PublishCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;

@SpringBootTest
class MqttDemoApplicationTests {

    @Autowired
    MqttUtil mqttUtil;

    @Test
    void pushMessageTest() throws Exception {
        for (int i = 0; i < 50; i++) {
            String topic = "VipSoft_MQTT";
            mqttUtil.publish(topic, "发送消息：" + System.currentTimeMillis() + i);
            Thread.sleep(3000);
        }
        new CountDownLatch(1).await();
    }
    @Autowired
    MqttClientManager mqttManager;

    @Test
    void subscribeTest() throws Exception {
        String topic = "VipSoft_MQTT";
        MqttClient mqttClient = mqttManager.getConnection().getMqttClient();
        //这里的setCallback需要新建一个Callback类并实现 MqttCallback 这个类
        mqttClient.setCallback(new PublishCallback());
        while (true) {
            mqttClient.subscribe(topic, (topic1, message) -> System.out.println("message = " + new String( message.getPayload())));
            Thread.sleep(1000);
        }
    }
}
