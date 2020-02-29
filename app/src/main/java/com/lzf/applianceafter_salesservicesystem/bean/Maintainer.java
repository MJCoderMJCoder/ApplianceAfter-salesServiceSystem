package com.lzf.applianceafter_salesservicesystem.bean;

/**
 * CREATE TABLE `maintainer` (
 * `maintainer_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '维修工ID',
 * `maintainer_name` varchar(45) NOT NULL COMMENT '维修工名称',
 * `maintainer_phone` varchar(45) NOT NULL COMMENT '维修工手机',
 * `maintainer_gender` varchar(4) NOT NULL COMMENT '维修工性别',
 * `maintainer_address` varchar(200) NOT NULL COMMENT '维修工地址',
 * `maintainer_pwd` varchar(45) NOT NULL,
 * PRIMARY KEY (`maintainer_id`),
 * UNIQUE KEY `user_id_UNIQUE` (`maintainer_id`),
 * UNIQUE KEY `maintainer_phone_UNIQUE` (`maintainer_phone`)
 * ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='维修工/维修人员表'
 */
public class Maintainer {
    private int maintainer_id;
    private String maintainer_name;
    private String maintainer_phone;
    private String maintainer_gender;
    private String maintainer_address;
    private String maintainer_pwd;

    public Maintainer() {
    }

    public Maintainer(int maintainer_id, String maintainer_name, String maintainer_phone, String maintainer_gender, String maintainer_address, String maintainer_pwd) {
        this.maintainer_id = maintainer_id;
        this.maintainer_name = maintainer_name;
        this.maintainer_phone = maintainer_phone;
        this.maintainer_gender = maintainer_gender;
        this.maintainer_address = maintainer_address;
        this.maintainer_pwd = maintainer_pwd;
    }

    public int getMaintainer_id() {
        return maintainer_id;
    }

    public void setMaintainer_id(int maintainer_id) {
        this.maintainer_id = maintainer_id;
    }

    public String getMaintainer_name() {
        return maintainer_name;
    }

    public void setMaintainer_name(String maintainer_name) {
        this.maintainer_name = maintainer_name;
    }

    public String getMaintainer_phone() {
        return maintainer_phone;
    }

    public void setMaintainer_phone(String maintainer_phone) {
        this.maintainer_phone = maintainer_phone;
    }

    public String getMaintainer_gender() {
        return maintainer_gender;
    }

    public void setMaintainer_gender(String maintainer_gender) {
        this.maintainer_gender = maintainer_gender;
    }

    public String getMaintainer_address() {
        return maintainer_address;
    }

    public void setMaintainer_address(String maintainer_address) {
        this.maintainer_address = maintainer_address;
    }

    public String getMaintainer_pwd() {
        return maintainer_pwd;
    }

    public void setMaintainer_pwd(String maintainer_pwd) {
        this.maintainer_pwd = maintainer_pwd;
    }

    @Override
    public String toString() {
        return "Maintainer{" +
                "maintainer_id=" + maintainer_id +
                ", maintainer_name='" + maintainer_name + '\'' +
                ", maintainer_phone='" + maintainer_phone + '\'' +
                ", maintainer_gender='" + maintainer_gender + '\'' +
                ", maintainer_address='" + maintainer_address + '\'' +
                ", maintainer_pwd='" + maintainer_pwd + '\'' +
                '}';
    }
}
