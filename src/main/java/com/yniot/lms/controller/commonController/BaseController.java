package com.yniot.lms.controller.commonController;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.yniot.lms.db.cachce.CacheDao;
import com.yniot.lms.db.entity.User;
import com.yniot.lms.enums.OperatorEnum;
import com.yniot.lms.service.LoginHistoryService;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import sun.misc.Cache;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @Author wanggl
 * @Description 通用方法
 * @create 10:55 2018-8-15
 * @modify 15:2 2018-11-22
 **/
public class BaseController {
    protected static Logger logger = Logger.getLogger(BaseController.class);

    protected JSONObject result = new JSONObject();
    protected static String DATA_KEY = "data";
    public final static String PAGE_SIZE_KEY = "pageSize";
    public final static String PAGE_NUM_KEY = "pageNum";
    public final static String KEY_WORD_KEY = "keyWord";
    protected static String TOTAL_PAGE_NUM_KEY = "totalPageNum";
    protected static String TOTAL_NUM_KEY = "totalNum";

    protected static String ERROR_MSG_KEY = "errorMsg";
    protected static String STATUS_KEY = "status";
    protected static String RESULT_KEY = "result";
    protected static String DEFAULT_ERROR_MSG = "内部错误!";
    protected static int DEFAULT_SUCCESS_STATUS = 201;

    /**
     * 返回成功信息
     *
     * @param data
     * @return
     */
    public String getSuccessResult(Object data) {
        long pageNum = 1;
        long pageSize = 0;
        long totalNum = 0;
        if (data instanceof List) {
            pageSize = ((List) data).size();
            totalNum = pageSize;
        }
        return this.getResult(true, data, "", pageNum, pageSize, totalNum);
    }

    public String getJsonObj(Object data) {
        return JSONObject.toJSONString(data, SerializerFeature.WriteMapNullValue);
    }


    public String getSuccessResult(Object data, long pageNum, long pageSize, long total) {
        return this.getResult(true, data, "", pageNum, pageSize, total);
    }


    /**
     * 返回错误信息
     *
     * @param data
     * @param errorMessage
     * @return
     */
    public String getErrorResult(Object data, String errorMessage) {
        return this.getResult(false, data, errorMessage, 0, 0, 0);
    }

    public String getErrorResult(Object data) {
        return this.getResult(false, data, "", 0, 0, 0);
    }

    public String getErrorMsg(String msg) {
        return this.getResult(false, null, msg, 0, 0, 0);
    }

    public String getError() {
        return this.getResult(false, null, null, 0, 0, 0);
    }


    private String getResult(boolean successFlag, Object data, String errorMessage, long pageNum, long pageSize, long totalNum) {
        if (successFlag) {
            this.result.put(STATUS_KEY, DEFAULT_SUCCESS_STATUS);
            this.result.put(DATA_KEY, data);
            this.result.put(PAGE_SIZE_KEY, pageSize);
            this.result.put(PAGE_NUM_KEY, pageNum);
            long totalPageNum = pageSize > 0 ? (totalNum / pageSize + (totalNum % pageSize > 0 ? 1 : 0)) : 0;
            this.result.put(TOTAL_PAGE_NUM_KEY, totalPageNum);
            this.result.put(TOTAL_NUM_KEY, totalNum);
        } else {
            logger.info("errorMessage:[" + errorMessage + "]");
            this.result.put(ERROR_MSG_KEY, errorMessage != null && errorMessage.isEmpty() ? DEFAULT_ERROR_MSG : errorMessage);
        }
        this.result.put(RESULT_KEY, successFlag);
        return JSONObject.toJSONString(this.result, SerializerFeature.WriteMapNullValue);
    }


    public String getToken(String token) {
        this.result.put("token", token);
        return JSONObject.toJSONString(this.result, SerializerFeature.WriteMapNullValue);
    }


    public ResponseEntity<byte[]> getFile(String path, String fileName) throws IOException {
        File file = new File(path + "/" + fileName);
        if (!file.exists()) {
            return null;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
                headers, HttpStatus.CREATED);
    }


    public User getUser() {
        return (User) SecurityUtils.getSubject().getPrincipal();
    }

    public boolean isUser() {
        User user = this.getUser();
        if (user.getUserType() == OperatorEnum.USER.getType()) {
            return true;
        }
        return false;
    }


    public boolean isLaundry() {
        User user = this.getUser();
        if (user.getUserType() == OperatorEnum.LAUNDRY.getType()) {
            return true;
        }
        return false;
    }


    public boolean isMailMan() {
        User user = this.getUser();
        if (user.getUserType() == OperatorEnum.MAIL_MAN.getType()) {
            return true;
        }
        return false;
    }


    public boolean isAdmin() {
        User user = this.getUser();
        if (user.getUserType() == OperatorEnum.ADMIN.getType()) {
            return true;
        }
        return false;
    }

    public String noAuth() {
        return this.getErrorMsg("没有权限!");
    }

    public String noLogin() {
        return this.getErrorMsg("登陆!");
    }

    public String stateWrong() {
        return this.getErrorMsg("当前状态不允许该操作!");
    }


}
