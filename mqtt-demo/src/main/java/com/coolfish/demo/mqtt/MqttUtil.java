package com.coolfish.demo.mqtt;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @className: MqttUtil
 * @description: TODO 类描述
 * @author: xufh
 * @date: 2023/12/13
 */
@Component
public class MqttUtil {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MqttClientManager mqttManager;

    public void publish(String topic, String message) {
        logger.info("publish INFO ; topic={}, message={}", topic, message);
        MqttConnection connection = null;
        try {
            connection = mqttManager.getConnection();
            logger.info("publish INFO ; topic={},targetUrl={}", topic, connection.getMqttClient().getServerURI());
            connection.publish(topic, message);
        } catch (Exception e) {
            logger.error("publish ERROR ; topic={},message={},error={}", topic, message, e.getMessage());
        } finally {
            if (null != connection) {
                connection.close();
            }
        }
    }


}
