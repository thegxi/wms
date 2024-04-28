package com.linlu.wms.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 用户信息表
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 用户ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 部门ID
     */
    @TableField(value = "dept_id")
    private String dept_id;

    /**
     * 用户账号
     */
    @TableField(value = "user_name")
    private String user_name;

    /**
     * 
用户昵称
     */
    @TableField(value = "nick_name")
    private String nick_name;

    /**
     * 用
户类型（00系统用户）
     */
    @TableField(value = "user_type")
    private String user_type;

    /**
     * 用户邮
箱
     */
    @TableField(value = "email")
    private String email;

    /**
     * 
手机号码
     */
    @TableField(value = "phone_number")
    private String phone_number;

    /**
     * 用户性别（0
男 1女 2未知）
     */
    @TableField(value = "sex")
    private String sex;

    /**
     * 头像
地址
     */
    @TableField(value = "avatar")
    private String avatar;

    /**
     * 密
码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 帐号状态
（0正常 1停用）
     */
    @TableField(value = "status")
    private String status;

    /**
     * 删除标
志（0代表存在 2代表删除）
     */
    @TableField(value = "del_flag")
    private String del_flag;

    /**
     * 最
后登录IP
     */
    @TableField(value = "login_ip")
    private String login_ip;

    /**
     * 最后登录时间
     */
    @TableField(value = "login_date")
    private LocalDateTime login_date;

    /**
     * 创建者
     */
    @TableField(value = "create_by")
    private String create_by;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime create_time;

    /**
     * 更新者
     */
    @TableField(value = "update_by")
    private String update_by;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private LocalDateTime update_time;

    /**
     * 备
注
     */
    @TableField(value = "remark")
    private String remark;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        User other = (User) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getDept_id() == null ? other.getDept_id() == null : this.getDept_id().equals(other.getDept_id()))
            && (this.getUser_name() == null ? other.getUser_name() == null : this.getUser_name().equals(other.getUser_name()))
            && (this.getNick_name() == null ? other.getNick_name() == null : this.getNick_name().equals(other.getNick_name()))
            && (this.getUser_type() == null ? other.getUser_type() == null : this.getUser_type().equals(other.getUser_type()))
            && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
            && (this.getPhone_number() == null ? other.getPhone_number() == null : this.getPhone_number().equals(other.getPhone_number()))
            && (this.getSex() == null ? other.getSex() == null : this.getSex().equals(other.getSex()))
            && (this.getAvatar() == null ? other.getAvatar() == null : this.getAvatar().equals(other.getAvatar()))
            && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getDel_flag() == null ? other.getDel_flag() == null : this.getDel_flag().equals(other.getDel_flag()))
            && (this.getLogin_ip() == null ? other.getLogin_ip() == null : this.getLogin_ip().equals(other.getLogin_ip()))
            && (this.getLogin_date() == null ? other.getLogin_date() == null : this.getLogin_date().equals(other.getLogin_date()))
            && (this.getCreate_by() == null ? other.getCreate_by() == null : this.getCreate_by().equals(other.getCreate_by()))
            && (this.getCreate_time() == null ? other.getCreate_time() == null : this.getCreate_time().equals(other.getCreate_time()))
            && (this.getUpdate_by() == null ? other.getUpdate_by() == null : this.getUpdate_by().equals(other.getUpdate_by()))
            && (this.getUpdate_time() == null ? other.getUpdate_time() == null : this.getUpdate_time().equals(other.getUpdate_time()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getDept_id() == null) ? 0 : getDept_id().hashCode());
        result = prime * result + ((getUser_name() == null) ? 0 : getUser_name().hashCode());
        result = prime * result + ((getNick_name() == null) ? 0 : getNick_name().hashCode());
        result = prime * result + ((getUser_type() == null) ? 0 : getUser_type().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getPhone_number() == null) ? 0 : getPhone_number().hashCode());
        result = prime * result + ((getSex() == null) ? 0 : getSex().hashCode());
        result = prime * result + ((getAvatar() == null) ? 0 : getAvatar().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getDel_flag() == null) ? 0 : getDel_flag().hashCode());
        result = prime * result + ((getLogin_ip() == null) ? 0 : getLogin_ip().hashCode());
        result = prime * result + ((getLogin_date() == null) ? 0 : getLogin_date().hashCode());
        result = prime * result + ((getCreate_by() == null) ? 0 : getCreate_by().hashCode());
        result = prime * result + ((getCreate_time() == null) ? 0 : getCreate_time().hashCode());
        result = prime * result + ((getUpdate_by() == null) ? 0 : getUpdate_by().hashCode());
        result = prime * result + ((getUpdate_time() == null) ? 0 : getUpdate_time().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", dept_id=").append(dept_id);
        sb.append(", user_name=").append(user_name);
        sb.append(", nick_name=").append(nick_name);
        sb.append(", user_type=").append(user_type);
        sb.append(", email=").append(email);
        sb.append(", phone_number=").append(phone_number);
        sb.append(", sex=").append(sex);
        sb.append(", avatar=").append(avatar);
        sb.append(", password=").append(password);
        sb.append(", status=").append(status);
        sb.append(", del_flag=").append(del_flag);
        sb.append(", login_ip=").append(login_ip);
        sb.append(", login_date=").append(login_date);
        sb.append(", create_by=").append(create_by);
        sb.append(", create_time=").append(create_time);
        sb.append(", update_by=").append(update_by);
        sb.append(", update_time=").append(update_time);
        sb.append(", remark=").append(remark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}