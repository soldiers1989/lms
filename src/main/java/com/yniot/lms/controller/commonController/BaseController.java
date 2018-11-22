package com.yniot.lms.controller.commonController;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

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
    private static Logger logger = Logger.getLogger(BaseController.class);

    private JSONObject result = new JSONObject();
    private static String DATA_KEY = "data";
    private static String PAGE_SIZE_KEY = "size";
    private static String PAGE_NUM_KEY = "pageNum";
    private static String TOTAL_PAGE_NUM_KEY = "totalPageNum";
    private static String TOTAL_NUM_KEY = "totalNum";

    private static String ERROR_MSG_KEY = "errorMsg";
    private static String STATUS_KEY = "status";
    private static String RESULT_KEY = "result";
    private static String DEFAULT_ERROR_MSG = "内部错误!";
    private static int DEFAULT_SUCCESS_STATUS = 201;

    /**
     * 返回成功信息
     *
     * @param data
     * @return
     */
    public String getSuccessResult(Object data) {
        int pageNum = 1;
        int pageSize = 0;
        int totalNum = 0;
        if (data instanceof List) {
            pageSize = ((List) data).size();
            totalNum = pageSize;
        }
        return this.getResult(true, data, "", pageNum, pageSize, totalNum);
    }

    public String getJsonObj(Object data) {
        return JSONObject.toJSONString(data, SerializerFeature.WriteMapNullValue);
    }


    public String getSuccessResult(Object data, int pageNum, int pageSize, long total) {
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


    private String getResult(boolean successFlag, Object data, String errorMessage, int pageNum, int pageSize, long totalNum) {
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
}
