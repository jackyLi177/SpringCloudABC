package com.jacky.auth_center.util;

import com.jacky.auth_center.model.DO.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Objects;

/**
 * @author Liyj
 * @desc
 * @date 2021/4/1
 */
public class ShiroUtil {

    public static SysUser getCurrentUser() {
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        if (Objects.isNull(principals)) {
            return null;
        }else {
            return (SysUser) principals.getPrimaryPrincipal();
        }
    }

}
