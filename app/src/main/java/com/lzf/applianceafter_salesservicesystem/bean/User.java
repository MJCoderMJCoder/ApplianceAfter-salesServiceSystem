package com.lzf.applianceafter_salesservicesystem.bean;

/**
 * CREATE TABLE `maintainer` (
 * `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '维修工ID',
 * `user_name` varchar(45) NOT NULL COMMENT '维修工名称',
 * `user_phone` varchar(45) NOT NULL COMMENT '维修工手机',
 * `user_gender` varchar(4) NOT NULL COMMENT '维修工性别',
 * `user_address` varchar(200) NOT NULL COMMENT '维修工地址',
 * `user_pwd` varchar(45) NOT NULL,
 * PRIMARY KEY (`user_id`),
 * UNIQUE KEY `user_id_UNIQUE` (`user_id`),
 * UNIQUE KEY `user_phone_UNIQUE` (`user_phone`)
 * ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='维修工/维修人员表'
 */
public class User {
    private int user_id;
    private String user_name;
    private String user_phone;
    private String user_gender;
    private String user_address;
    private String user_pwd;

    public User() {
    }

    public User(int user_id, String user_name, String user_phone, String user_gender, String user_address, String user_pwd) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_phone = user_phone;
        this.user_gender = user_gender;
        this.user_address = user_address;
        this.user_pwd = user_pwd;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_gender() {
        return user_gender;
    }

    public void setUser_gender(String user_gender) {
        this.user_gender = user_gender;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    public String getUser_pwd() {
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

    @Override
    public String toString() {
        return "Maintainer{" +
                "user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                ", user_phone='" + user_phone + '\'' +
                ", user_gender='" + user_gender + '\'' +
                ", user_address='" + user_address + '\'' +
                ", user_pwd='" + user_pwd + '\'' +
                '}';
    }
}
