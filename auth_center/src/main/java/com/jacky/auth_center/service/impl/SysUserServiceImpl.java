package com.jacky.auth_center.service.impl;

import com.jacky.auth_center.mapper.SysUserMapper;
import com.jacky.auth_center.model.DO.SysUser;
import com.jacky.auth_center.service.SysUserService;
import com.jacky.auth_center.util.ADESUtils;
import common.BizEnum;
import common.BizException;
import common.RespResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import util.RespResultUtil;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;
    @Autowired
    private ADESUtils adesUtils;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public int deleteByPrimaryKey(Long id) {
        return sysUserMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(SysUser record) {
        return sysUserMapper.insert(record);
    }

    @Override
    public int insertSelective(SysUser record) {
        return sysUserMapper.insertSelective(record);
    }

    @Override
    public SysUser selectByPrimaryKey(Long id) {
        return sysUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(SysUser record) {
        return sysUserMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SysUser record) {
        return sysUserMapper.updateByPrimaryKey(record);
    }

    @Override
    public RespResult register(SysUser user) {
        String uPass = user.getPass();
        if (StringUtils.isBlank(uPass)) {
            throw new BizException(BizEnum.PARAM_ERROR);
        }
        String encode = passwordEncoder.encode(uPass);
        user.setPass(encode);
        int insert = insert(user);
        if (insert > 0) {
            return RespResultUtil.success(user);
        }
        return RespResultUtil.error();
    }

    @Override
    public RespResult login(SysUser param) {
        SysUser user = sysUserMapper.getOneByUName(param.getName());
        if (Objects.isNull(user)) {
            throw new BizException("用户名无效！");
        }
        if (passwordEncoder.matches(param.getPass(), user.getPass())) {
            return RespResultUtil.success();
        }
        return RespResultUtil.success("密码错误");
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pass = "123456";
        String encode = encoder.encode(pass);
        System.out.println(encode);
        System.out.println(encoder.matches(pass, encode));
    }

}


