package com.coolfish.demo.mqtt;

/**
 * @className: MqttPoolConfig
 * @description: TODO 类描述
 * @author: xufh
 * @date: 2023/12/13
 */
public class MqttPoolConfig {

    /**
     * 是否启用自定义配置
     */
    private boolean customSet;
    /**
     * 最小的空闲连接数
     */
    private int minIdle;
    /**
     * 最大的空闲连接数
     */
    private int maxIdle;
    /**
     * 最大连接数
     */
    private int maxTotal;

    public boolean isCustomSet() {
        return customSet;
    }

    public void setCustomSet(boolean customSet) {
        this.customSet = customSet;
    }

    public int getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(int minIdle) {
        this.minIdle = minIdle;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public int getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }
}
