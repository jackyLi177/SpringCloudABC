package com.jacky.auth_center.service.impl;

import com.jacky.auth_center.mapper.SysUserMapper;
import com.jacky.auth_center.model.DO.SysUser;
import com.jacky.auth_center.service.SysUserService;
import com.jacky.auth_center.util.ADESUtils;
import com.jacky.auth_center.util.ShiroUtil;
import common.BizEnum;
import common.BizException;
import common.RespResult;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import util.RespResultUtil;

import javax.annotation.Resource;
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
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(param.getName(),param.getPass());
        try{
            subject.login(usernamePasswordToken);
        }catch  ( UnknownAccountException uae ) {
            throw new BizException(uae.getMessage());
        } catch  ( IncorrectCredentialsException ice ) {
            throw new BizException(ice.getMessage());
        } catch  ( LockedAccountException lae ) {
            throw new BizException(lae.getMessage());
        } catch  ( ExcessiveAttemptsException eae ) {
            throw new BizException(eae.getMessage());
        }catch ( AuthenticationException ae ) {
            throw new BizException(ae.getMessage());
        }catch (AuthorizationException e){
            throw new BizException(e.getMessage());
        }catch (Exception e){
            throw new BizException(e.getMessage());
        }
        return RespResultUtil.success("登陆成功");
    }

    @Override
    public RespResult logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return RespResultUtil.success();
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

}


