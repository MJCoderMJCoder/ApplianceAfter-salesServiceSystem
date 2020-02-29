package com.lzf.applianceafter_salesservicesystem.bean;

import java.io.Serializable;

/**
 * CREATE TABLE `maintenance` (
 * `maintenance_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '维修申请ID',
 * `maintenance_name` varchar(45) NOT NULL COMMENT '维修申请名称',
 * `maintenance_address` varchar(200) NOT NULL COMMENT '维修申请地址',
 * `maintenance_appliance` varchar(200) NOT NULL COMMENT '维修申请的家电信息',
 * `maintenance_user` int(11) NOT NULL COMMENT '维修申请的发起用户ID',
 * `maintenance_status` int(2) NOT NULL COMMENT '0-未接取【用户】/可接取【维修工】\\\\n1-已接取【用户】/未完成【维修工】\\\\n3-已取消\\\\n4-已完成\\\\n',
 * `maintenance_maintainer` int(11) DEFAULT NULL COMMENT '接取维修申请的维修工ID',
 * PRIMARY KEY (`maintenance_id`),
 * UNIQUE KEY `maintenance_request_id_UNIQUE` (`maintenance_id`),
 * KEY `maintenance_user_idx` (`maintenance_user`),
 * KEY `maintenance_maintainer_foreign_idx` (`maintenance_maintainer`),
 * CONSTRAINT `maintenance_maintainer_foreign` FOREIGN KEY (`maintenance_maintainer`) REFERENCES `maintainer` (`maintainer_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
 * CONSTRAINT `maintenance_user_foreign` FOREIGN KEY (`maintenance_user`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
 * ) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COMMENT='维修申请表'
 */
public class Maintenance implements Serializable {
    private static final long serialVersionUID = -3650413031748859924L;
    private int maintenance_id;
    private String maintenance_name;
    private String maintenance_address;
    private String maintenance_appliance;
    private int maintenance_user;
    private int maintenance_status;
    private int maintenance_maintainer;

    public Maintenance() {
    }

    public Maintenance(int maintenance_id, String maintenance_name, String maintenance_address, String maintenance_appliance, int maintenance_user, int maintenance_status, int maintenance_maintainer) {
        this.maintenance_id = maintenance_id;
        this.maintenance_name = maintenance_name;
        this.maintenance_address = maintenance_address;
        this.maintenance_appliance = maintenance_appliance;
        this.maintenance_user = maintenance_user;
        this.maintenance_status = maintenance_status;
        this.maintenance_maintainer = maintenance_maintainer;
    }


    public int getMaintenance_id() {
        return maintenance_id;
    }

    public void setMaintenance_id(int maintenance_id) {
        this.maintenance_id = maintenance_id;
    }

    public String getMaintenance_name() {
        return maintenance_name;
    }

    public void setMaintenance_name(String maintenance_name) {
        this.maintenance_name = maintenance_name;
    }

    public String getMaintenance_address() {
        return maintenance_address;
    }

    public void setMaintenance_address(String maintenance_address) {
        this.maintenance_address = maintenance_address;
    }

    public String getMaintenance_appliance() {
        return maintenance_appliance;
    }

    public void setMaintenance_appliance(String maintenance_appliance) {
        this.maintenance_appliance = maintenance_appliance;
    }

    public int getMaintenance_user() {
        return maintenance_user;
    }

    public void setMaintenance_user(int maintenance_user) {
        this.maintenance_user = maintenance_user;
    }

    public int getMaintenance_status() {
        return maintenance_status;
    }

    public void setMaintenance_status(int maintenance_status) {
        this.maintenance_status = maintenance_status;
    }

    public int getMaintenance_maintainer() {
        return maintenance_maintainer;
    }

    public void setMaintenance_maintainer(int maintenance_maintainer) {
        this.maintenance_maintainer = maintenance_maintainer;
    }


    @Override
    public String toString() {
        return "Maintenance{" +
                "maintenance_id=" + maintenance_id +
                ", maintenance_name='" + maintenance_name + '\'' +
                ", maintenance_address='" + maintenance_address + '\'' +
                ", maintenance_appliance='" + maintenance_appliance + '\'' +
                ", maintenance_user=" + maintenance_user +
                ", maintenance_status=" + maintenance_status +
                ", maintenance_maintainer=" + maintenance_maintainer +
                '}';
    }
}
