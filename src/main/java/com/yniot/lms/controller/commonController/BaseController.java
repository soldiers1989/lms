package com.yniot.lms.controller.commonController;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.yniot.lms.db.entity.User;
import com.yniot.lms.enums.ErrorMsgEnum;
import com.yniot.lms.enums.UserTypeEnum;
import com.yniot.lms.security.CustomSessionKey;
import com.yniot.lms.service.UserService;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @Author wanggl
 * @Description 通用方法
 * @create 10:55 2018-8-15
 * @modify 15:2 2018-11-29
 **/
public class BaseController {

    protected static Logger logger = Logger.getLogger(BaseController.class);

    protected JSONObject result = new JSONObject();
    public static String DATA_KEY = "data";
    public final static String PAGE_SIZE_KEY = "pageSize";
    public final static String PAGE_NUM_KEY = "pageNum";
    public final static String KEY_WORD_KEY = "keyWord";
    public static String TOTAL_PAGE_NUM_KEY = "totalPageNum";
    public static String TOTAL_NUM_KEY = "totalNum";

    public static String ERROR_MSG_KEY = "errorMsg";
    public static String STATUS_KEY = "status";
    public static String RESULT_KEY = "result";
    public static String DEFAULT_ERROR_MSG = "内部错误!";
    public static int DEFAULT_SUCCESS_STATUS = 200;
    public static int NEED_LOGIN = 401;
    public static final String AUTHORIZATION = "Authorization";

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

    public String getJsonStr(Object data) {
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

    public String getErrorMsg(String msg, int status) {
        return this.getResult(false, null, msg, status, 0, 0, 0);
    }

    public String getErrorMsg(ErrorMsgEnum errorMsgEnum) {
        return this.getResult(false, null, errorMsgEnum.getName(), errorMsgEnum.getCode(), 0, 0, 0);
    }

    public String getError() {
        return this.getResult(false, null, null, 0, 0, 0);
    }


    private String getResult(boolean successFlag, Object data, String errorMessage, long pageNum, long pageSize, long totalNum) {
        return this.getResult(successFlag, data, errorMessage, DEFAULT_SUCCESS_STATUS, pageNum, pageSize, totalNum);
    }

    private String getResult(boolean successFlag, Object data, String errorMessage, int status, long pageNum, long pageSize, long totalNum) {
        if (successFlag) {
            this.result.put(STATUS_KEY, status);
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


    public String tokenAndUser(String token, Object user) {
        this.result.put("token", token);
        this.result.put("userInfo", user);
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

    @Autowired
    UserService userService;

    public User getUser() {

        return userService.getById(1);
//        return (User) SecurityUtils.getSubject().getPrincipal();
    }

    public int getId() {
//        return getUser().getId();
        return 1;

    }

    public User getUser(String sessionId) {
        Session session = SecurityUtils.getSecurityManager().getSession(new CustomSessionKey(sessionId));
        session.getHost();
        return (User) SecurityUtils.getSubject().getPrincipal();
    }

    public int getId(String token) {
        return getUser(token).getId();
    }

    public boolean isUser() {
        User user = this.getUser();
        if (user.getUserType() == UserTypeEnum.USER.getType()) {
            return true;
        }
        return false;
    }

    public boolean isLogin() {
        User user = this.getUser();
        return user != null;
    }


    public boolean isLaundry() {
        User user = this.getUser();
        if (user.getUserType() == UserTypeEnum.LAUNDRY.getType()) {
            return true;
        }
        return false;
    }

    public boolean isAdminOrLaundry() {
        return isLaundry() || isAdmin();
    }

    public boolean isMailMan() {
        User user = this.getUser();
        if (user.getUserType() == UserTypeEnum.MAIL_MAN.getType()) {
            return true;
        }
        return false;
    }


    public boolean isAdmin() {
        User user = this.getUser();
        if (user.getUserType() == UserTypeEnum.ADMIN.getType()) {
            return true;
        }
        return false;
    }

    public String noAuth() {
        return this.getErrorMsg("没有权限!");
    }

    public String noLogin() {
        return this.getErrorMsg("请先登陆!", NEED_LOGIN);
    }

    public String wrongState() {
        return this.getErrorMsg("当前状态不允许该操作!");
    }


    public String decryptFail() {
        return this.getErrorMsg("解密失败!");
    }

    public String expired() {
        return this.getErrorMsg("订单已超时!");
    }


}
