package common;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description
 * @Author liyj
 * @Date 2020/11/30 4:11 下午
 */
public enum BizEnum {

    SERVER_BUSY(-1,"服务器繁忙"),

    PARAM_ERROR(-1,"非法参数"),

    OK(0,"成功")

    ;

    @Getter
    @Setter
    private Integer code;

    @Getter
    @Setter
    private String message;

    private BizEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }

}
