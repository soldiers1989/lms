package com.yniot.lms.exception;

import com.yniot.lms.controller.commonController.BaseController;
import com.yniot.lms.db.cachce.CacheDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @project: gms
 * @description: 全局异常记录
 * @author: wanggl
 * @create: 2018-11-02 09:19
 **/
@ControllerAdvice
public class GlobalExceptionHandler extends BaseController {
    private static Logger logger = Logger.getLogger(GlobalExceptionHandler.class);

    @Autowired
    private CacheDao cacheDao;

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public String errorRecorder(Exception e) throws InterruptedException {
        String errorMsg = e.getMessage();
        logger.error(errorMsg);
        return super.getErrorMsg(errorMsg);
    }
}
