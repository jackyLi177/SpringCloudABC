package com.jacky.auth_center.service;

import com.jacky.auth_center.model.DO.SysUser;
import common.RespResult;

public interface SysUserService {


    int deleteByPrimaryKey(Long id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    RespResult register(SysUser user);

    RespResult login(SysUser user);

    SysUser getByName(String name);

}


