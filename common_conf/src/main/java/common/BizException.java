package common;

import lombok.Data;

/**
 * @Description
 * @Author liyj
 * @Date 2020/11/30 4:08 下午
 */
@Data
public class BizException extends RuntimeException{

    private Integer code;

    private String message;

    public BizException(){}

    public BizException(Integer code,String message){
        this.code = code;
        this.message = message;
    }

    public BizException(String message){
        this.code = -1;
        this.message = message;
    }

    public BizException(BizEnum bizEnum){
        this.code = bizEnum.getCode();
        this.message = bizEnum.getMessage();
    }

}
