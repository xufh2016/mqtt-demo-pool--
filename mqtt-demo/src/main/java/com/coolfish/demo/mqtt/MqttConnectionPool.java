package com.coolfish.demo.mqtt;


import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * @className: MqttConnectionPool
 * @description: TODO 类描述
 * @author: xufh
 * @date: 2023/12/13
 */
public class MqttConnectionPool extends GenericObjectPool<MqttConnection> {

    public MqttConnectionPool(MqttConnectionFactory factory, GenericObjectPoolConfig config) {
        super(factory, config);
    }

    /**
     * 从对象池获得一个对象
     */
    @Override
    public MqttConnection borrowObject() throws Exception {
        MqttConnection conn = super.borrowObject();
        // 设置所属连接池
        if (conn.getBelongedPool() == null) {
            conn.setBelongedPool(this);
        }
        return conn;
    }

    /**
     * 归还一个连接对象
     *
     * @param conn
     */
    @Override
    public void returnObject(MqttConnection conn) {
        if (conn != null) {
            super.returnObject(conn);
        }
    }
}