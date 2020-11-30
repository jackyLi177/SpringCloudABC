package common;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import util.RespResultUtil;

/**
 * @Description
 * @Author liyj
 * @Date 2020/11/30 5:25 下午
 */
@ControllerAdvice
public class BizExceptionController {

    @ResponseBody
    @ExceptionHandler(BizException.class)
    public RespResult BizExceptionHandler(BizException bizException){
        return RespResultUtil.error(bizException.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public RespResult exceptionHandler(Exception exception){
        return RespResultUtil.error(exception.getMessage());
    }

}
