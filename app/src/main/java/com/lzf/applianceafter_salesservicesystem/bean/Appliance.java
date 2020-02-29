package com.lzf.applianceafter_salesservicesystem.bean;

import java.io.Serializable;

/**
 * CREATE TABLE `appliance` (
 * `appliance_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '家电ID',
 * `appliance_name` varchar(45) NOT NULL COMMENT '家电名称',
 * `appliance_model` varchar(45) NOT NULL COMMENT '家电型号',
 * `appliance_part` varchar(45) NOT NULL COMMENT '家电零件名称',
 * `appliance_price` float NOT NULL COMMENT '家电价格',
 * `appliance_arg` varchar(200) NOT NULL COMMENT '家电参数',
 * UNIQUE KEY `appliance_id_UNIQUE` (`appliance_id`)
 * ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='家电表'
 */
public class Appliance implements Serializable {
    private static final long serialVersionUID = -8629741593186919794L;
    private int appliance_id;
    private String appliance_name;
    private String appliance_model;
    private String appliance_part;
    private float appliance_price;
    private String appliance_arg;

    public Appliance() {
    }

    public Appliance(int appliance_id, String appliance_name, String appliance_model, String appliance_part, float appliance_price, String appliance_arg) {
        this.appliance_id = appliance_id;
        this.appliance_name = appliance_name;
        this.appliance_model = appliance_model;
        this.appliance_part = appliance_part;
        this.appliance_price = appliance_price;
        this.appliance_arg = appliance_arg;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getAppliance_id() {
        return appliance_id;
    }

    public void setAppliance_id(int appliance_id) {
        this.appliance_id = appliance_id;
    }

    public String getAppliance_name() {
        return appliance_name;
    }

    public void setAppliance_name(String appliance_name) {
        this.appliance_name = appliance_name;
    }

    public String getAppliance_model() {
        return appliance_model;
    }

    public void setAppliance_model(String appliance_model) {
        this.appliance_model = appliance_model;
    }

    public String getAppliance_part() {
        return appliance_part;
    }

    public void setAppliance_part(String appliance_part) {
        this.appliance_part = appliance_part;
    }

    public float getAppliance_price() {
        return appliance_price;
    }

    public void setAppliance_price(float appliance_price) {
        this.appliance_price = appliance_price;
    }

    public String getAppliance_arg() {
        return appliance_arg;
    }

    public void setAppliance_arg(String appliance_arg) {
        this.appliance_arg = appliance_arg;
    }

    @Override
    public String toString() {
        return "Appliance{" +
                "appliance_id=" + appliance_id +
                ", appliance_name='" + appliance_name + '\'' +
                ", appliance_model='" + appliance_model + '\'' +
                ", appliance_part='" + appliance_part + '\'' +
                ", appliance_price=" + appliance_price +
                ", appliance_arg='" + appliance_arg + '\'' +
                '}';
    }
}
