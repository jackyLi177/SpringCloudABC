package com.jacky.auth_center.model.VO;

import com.jacky.auth_center.model.DO.SysUser;
import lombok.Data;

/**
 * @Description
 * @Author liyj
 * @Date 2020/11/30 4:01 下午
 */
@Data
public class SysUserVO extends SysUser {

    private String token;

}
