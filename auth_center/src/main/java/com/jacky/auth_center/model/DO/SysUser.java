package com.jacky.auth_center.model.DO;

import com.jacky.auth_center.common.DecryptField;
import com.jacky.auth_center.util.ADESUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.lang.reflect.Field;

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
    @DecryptField
    private String tel;

    /**
     * 用户身份证号
     */
    private String idNum;

    public SysUser(){}

    public String getTel(){
        try {
            Field tel = this.getClass().getDeclaredField("tel");
            if (tel.isAnnotationPresent(DecryptField.class)){
                return ADESUtils.decrypt(this.tel);
            }
        } catch (NoSuchFieldException e) {
            return this.tel;
        }
        return this.tel;
    }
}