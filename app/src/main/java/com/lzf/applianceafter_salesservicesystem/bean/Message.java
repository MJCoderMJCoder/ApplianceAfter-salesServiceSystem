package com.lzf.applianceafter_salesservicesystem.bean;

import java.io.Serializable;

/**
 * CREATE TABLE `message` (
 * `message_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '留言ID',
 * `maintenance` int(11) NOT NULL COMMENT '维修申请的ID',
 * `user` int(11) DEFAULT NULL COMMENT '留言发起人ID',
 * `user_name` varchar(45) DEFAULT NULL COMMENT '留言发起人名称',
 * `maintainer` int(11) DEFAULT NULL COMMENT '回复给某人的ID',
 * `maintainer_name` varchar(45) DEFAULT NULL COMMENT '回复给某人的名称',
 * `reply_message` int(11) DEFAULT NULL COMMENT '回复那条信息',
 * `message_content` varchar(100) NOT NULL COMMENT '留言内容',
 * PRIMARY KEY (`message_id`),
 * UNIQUE KEY `leave_words_id_UNIQUE` (`message_id`),
 * KEY `maintenance_foreign_idx` (`maintenance`),
 * KEY `originator_foreign_idx` (`user`),
 * KEY `maintainer_foreign_idx` (`maintainer`),
 * KEY `reply_message_foreign_idx` (`reply_message`),
 * CONSTRAINT `maintainer_foreign` FOREIGN KEY (`maintainer`) REFERENCES `maintainer` (`maintainer_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
 * CONSTRAINT `maintenance_foreign` FOREIGN KEY (`maintenance`) REFERENCES `maintenance` (`maintenance_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
 * CONSTRAINT `reply_message_foreign` FOREIGN KEY (`reply_message`) REFERENCES `message` (`message_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
 * CONSTRAINT `user_foreign` FOREIGN KEY (`user`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='维修人员和维修申请关联表/维修人员和用户留言关联表'
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 5074694576527686525L;
    private int message_id;
    private int maintenance;
    private int user;
    private String user_name;
    private int maintainer;
    private String maintainer_name;
    private int reply_message;
    private String message_content;

    public Message() {
    }

    public Message(int message_id, int maintenance, int user, String user_name, int maintainer, String maintainer_name, int reply_message, String message_content) {
        this.message_id = message_id;
        this.maintenance = maintenance;
        this.user = user;
        this.user_name = user_name;
        this.maintainer = maintainer;
        this.maintainer_name = maintainer_name;
        this.reply_message = reply_message;
        this.message_content = message_content;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getMessage_id() {
        return message_id;
    }

    public void setMessage_id(int message_id) {
        this.message_id = message_id;
    }

    public int getMaintenance() {
        return maintenance;
    }

    public void setMaintenance(int maintenance) {
        this.maintenance = maintenance;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getMaintainer() {
        return maintainer;
    }

    public void setMaintainer(int maintainer) {
        this.maintainer = maintainer;
    }

    public String getMaintainer_name() {
        return maintainer_name;
    }

    public void setMaintainer_name(String maintainer_name) {
        this.maintainer_name = maintainer_name;
    }

    public int getReply_message() {
        return reply_message;
    }

    public void setReply_message(int reply_message) {
        this.reply_message = reply_message;
    }

    public String getMessage_content() {
        return message_content;
    }

    public void setMessage_content(String message_content) {
        this.message_content = message_content;
    }

    @Override
    public String toString() {
        return "Message{" +
                "message_id=" + message_id +
                ", maintenance=" + maintenance +
                ", user=" + user +
                ", user_name='" + user_name + '\'' +
                ", maintainer=" + maintainer +
                ", maintainer_name='" + maintainer_name + '\'' +
                ", reply_message=" + reply_message +
                ", message_content='" + message_content + '\'' +
                '}';
    }
}
