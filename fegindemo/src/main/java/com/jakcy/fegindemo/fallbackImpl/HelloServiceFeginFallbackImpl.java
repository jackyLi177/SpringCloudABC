package com.jakcy.fegindemo.fallbackImpl;

import com.jakcy.fegindemo.feginclient.HelloServiceFegin;
import org.springframework.stereotype.Component;

/**
 * @author ：lyj
 * @date ：2019/4/26 16:33
 */
@Component
public class HelloServiceFeginFallbackImpl implements HelloServiceFegin {
    @Override
    public String fasfsaf() {
        return "sorry,the target server is not available";
    }
}
