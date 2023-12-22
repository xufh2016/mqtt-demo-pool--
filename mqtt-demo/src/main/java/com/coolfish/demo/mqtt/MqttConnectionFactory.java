package com.coolfish.demo.mqtt;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.system.HostInfo;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @className: MqttConnectionFactory
 * @description: TODO 类描述
 * @author: xufh
 * @date: 2023/12/13
 */
public class MqttConnectionFactory extends BasePooledObjectFactory<MqttConnection> {
    private static final Logger logger = LoggerFactory.getLogger(MqttConnectionFactory.class);
    // AtomicInteger是一个提供原子操作的Integer类，通过线程安全的方式操作加减
    private AtomicInteger counter = new AtomicInteger();

    /**
     * 连接地址
     */
    private String serverURI;
    /**
     * 当前服务IP
     */
    private String localHostIP;

    /**
     * mqtt连接配置
     */
    private MqttConnectOptions mqttConnectConfig;

    /**
     * 根据mqtt连接 配置创建工厂
     */
    public MqttConnectionFactory(String serverURI, MqttConnectOptions mqttConnectConfig) {
        this.serverURI = serverURI;
        this.mqttConnectConfig = mqttConnectConfig;
    }

    /**
     * 在对象池中创建对象
     *
     * @return
     * @throws Exception
     */
    @Override
    public MqttConnection create() throws Exception {
        // 实现线程安全避免在高并发的场景下出现clientId重复导致无法创建连接的情况
        int count = this.counter.addAndGet(1);

        // 根据ip+编号,生成唯一clientId
        String clientId = this.getLosthostIp() + "_" + DateUtil.thisMillsecond();

        // 创建MQTT连接对象
        MqttClient mqttClient = new MqttClient(serverURI, clientId);

        // 建立连接
        mqttClient.connect(mqttConnectConfig);

        // 构建mqttConnection对象
        MqttConnection mqttConnection = new MqttConnection(mqttClient);
        logger.info("在对象池中创建对象 {}", clientId);
        return mqttConnection;
    }

    /**
     * common-pool2 中创建了 DefaultPooledObject 对象对对象池中对象进行的包装。
     * 将我们自定义的对象放置到这个包装中，工具会统计对象的状态、创建时间、更新时间、返回时间、出借时间、使用时间等等信息进行统计
     *
     * @param mqttConnection
     * @return
     */
    @Override
    public PooledObject<MqttConnection> wrap(MqttConnection mqttConnection) {
        logger.info("封装默认返回类型 {}", mqttConnection.toString());
        return new DefaultPooledObject<>(mqttConnection);
    }

    /**
     * 销毁对象
     *
     * @param p 对象池
     * @throws Exception 异常
     */
    @Override
    public void destroyObject(PooledObject<MqttConnection> p) throws Exception {
        if (p == null) {
            return;
        }
        MqttConnection mqttConnection = p.getObject();
        logger.info("销毁对象 {}", p.getObject().getMqttClient());
        mqttConnection.destroy();
    }

    /**
     * 校验对象是否可用
     *
     * @param p 对象池
     * @return 对象是否可用结果，boolean
     */
    @Override
    public boolean validateObject(PooledObject<MqttConnection> p) {
        MqttConnection mqttConnection = p.getObject();
        boolean result = mqttConnection.getMqttClient().isConnected();
        logger.debug("validateObject serverURI {},client_id {},result {}", mqttConnection.getMqttClient().getServerURI(),
                mqttConnection.getMqttClient().getClientId(), result);
        return result;
    }

    /**
     * 激活钝化的对象系列操作
     *
     * @param p 对象池
     * @throws Exception 异常信息
     */
    @Override
    public void activateObject(PooledObject<MqttConnection> p) throws Exception {
        logger.info("激活钝化的对象 {}", p.getObject().getMqttClient());
    }

    /**
     * 钝化未使用的对象
     *
     * @param p 对象池
     * @throws Exception 异常信息
     */
    @Override
    public void passivateObject(PooledObject<MqttConnection> p) throws Exception {
        logger.info("钝化未使用的对象 {}", p.getObject().getMqttClient());
    }


    /**
     * 获取当前服务真实IP
     */
    private String getLosthostIp() {
        if (StrUtil.isNotBlank(this.localHostIP)) {
            return this.localHostIP;
        }
        HostInfo hostInfo = new HostInfo();
        this.localHostIP = hostInfo.getAddress();
        return this.localHostIP;
    }

}
