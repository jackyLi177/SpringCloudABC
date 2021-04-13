package com.jacky.auth_center.service.impl;

import com.jacky.auth_center.mapper.SysUserMapper;
import com.jacky.auth_center.model.DO.SysUser;
import com.jacky.auth_center.model.VO.SysUserVO;
import com.jacky.auth_center.service.SysUserService;
import com.jacky.auth_center.util.ADESUtils;
import com.jacky.auth_center.util.JwtUtil;
import common.BizEnum;
import common.BizException;
import common.RespResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
        SysUser user = sysUserMapper.getOneByName(param.getName());
        if (Objects.isNull(user)) {
            throw new BizException("用户名不正确");
        }
        String plainPass = param.getPass();
        if (passwordEncoder.matches(plainPass,user.getPass())) {
            String token = JwtUtil.sign(user.getName(), user.getPass(), JwtUtil.expire);
            SysUserVO sysUserVO = new SysUserVO();
            BeanUtils.copyProperties(user,sysUserVO);
            sysUserVO.setToken(token);
            return RespResultUtil.success(sysUserVO);
        }else {
            throw new BizException("用户名密码不匹配");
        }
    }

    @Override
    public SysUser getByName(String name) {
        return sysUserMapper.getOneByName(name);
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pass = "$2a$10$BvHAU/ltQQ46WIszZwEBruHCEbU.A30gCIGBE1AUrD86B9M1sMR9G";
        System.out.println(encoder.matches("admin", pass));
    }

    @Override
    public List<SysUser> getAll() {
        return sysUserMapper.getAll();
    }
}


