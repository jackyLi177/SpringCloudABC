package util;

import common.BizEnum;
import common.RespResult;

/**
 * @Description
 * @Author liyj
 * @Date 2020/11/30 4:45 下午
 */
public class RespResultUtil {

    public static RespResult success(){
        return new RespResult();
    }

    public static RespResult success(String message){
        return new RespResult(message);
    }

    public static RespResult success(String message,Object data){
        return new RespResult(0,message,data);
    }

    public static RespResult success(Object data){
        return new RespResult(data);
    }

    public static RespResult success(BizEnum bizEnum){
        return new RespResult(bizEnum);
    }

    public static RespResult error(){
        return new RespResult(BizEnum.SERVER_BUSY);
    }

    public static RespResult error(String message){
        return new RespResult(-1,message,null);
    }

    public static RespResult error(BizEnum bizEnum){
        return new RespResult(bizEnum);
    }

}
