package com.jacky.auth_center.model.DO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * sys_user
 */
@Data
@Builder
@AllArgsConstructor
public class SysUser {
    /**
     * id
     */
    private Long id;

    /**
     * 用户名
     */
    private String name;

    /**
     * 用户密码
     */
    private String pass;

    /**
     * 备注
     */
    private String remark;

    /**
     * 用户电话
     */
    private String tel;

    /**
     * 用户身份证号
     */
    private String idNum;

    public SysUser(){}
}