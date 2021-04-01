package com.jacky.auth_center.controller;

import com.jacky.auth_center.model.DO.SysUser;
import com.jacky.auth_center.util.ShiroUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @author Liyj
 * @desc
 * @date 2021/4/1
 */
@RestController
@RequestMapping("/index")
public class IndexController {

    @GetMapping("/index")
    public String index(){
        SysUser currentUser = ShiroUtil.getCurrentUser();
        return Objects.isNull(currentUser) ? null : currentUser.toString();
    }

}
