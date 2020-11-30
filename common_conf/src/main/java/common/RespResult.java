package common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author liyj
 * @Date 2020/11/30 4:40 下午
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespResult {

    private Integer code;

    private String message;

    private Object data;

    public RespResult(BizEnum bizEnum){
        this.code = bizEnum.getCode();
        this.message = bizEnum.getMessage();
    }

    public RespResult(String message){
        this.code = 0;
        this.message = message;
    }

    public RespResult(Object data){
        this.code = BizEnum.OK.getCode();
        this.message = BizEnum.OK.getMessage();
        this.data = data;
    }
}
