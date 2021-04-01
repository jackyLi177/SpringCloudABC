package com.jacky.auth_center.controller;

import com.jacky.auth_center.model.VO.SysUserVO;
import com.jacky.auth_center.service.SysUserService;
import common.RespResult;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description 用户管理
 * @Author liyj
 * @Date 2020/11/30 3:57 下午
 */
@RestController
@RequestMapping("/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/register")
    public RespResult register(@RequestBody SysUserVO vo){
        return sysUserService.register(vo);
    }

    @PostMapping("/login")
    public RespResult login(@RequestBody SysUserVO vo){
        return sysUserService.login(vo);
    }

    @GetMapping
    public RespResult logout() {
        return sysUserService.logout();
    }

}
