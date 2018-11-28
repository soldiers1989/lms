package com.yniot.lms.utils;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Auther: lane
 * @Date: 2018/11/28 16:08
 * @Description:
 * @Version 1.0.0
 */
@Component
public class MessageUtil {
    @Value("${message.aliyun.accessKeyId}")
    private String accessKeyId;
    @Value("${message.aliyun.accessKeySecret}")
    private String accessKeySecret;
    @Value("${message.aliyun.product}")
    private String product;
    @Value("${message.aliyun.domain}")
    private String domain;
    @Value("${message.aliyun.signName}")
    private String signName;
    @Value("${message.aliyun.readTimeout}")
    private String readTimeout;
    @Value("${message.aliyun.connectTimeout}")
    private String connectTimeout;
    @Value("${message.aliyun.regionId}")
    private String regionId;

    public boolean sendRegisterCode(String phone, String code) throws ClientException {
        if (!CommonUtil.String.isPhone(phone)) {
            return false;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        send("SMS_152085241", jsonObject, phone);
        return true;
    }


    private SendSmsResponse send(String templateId, JSONObject templateParams, String phone) throws ClientException {
        System.setProperty("sun.net.client.defaultConnectTimeout", connectTimeout);
        System.setProperty("sun.net.client.defaultReadTimeout", readTimeout);
        IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint(regionId, regionId, product, domain);
        SendSmsRequest request = new SendSmsRequest();
        IAcsClient acsClient = new DefaultAcsClient(profile);
        request.setPhoneNumbers(phone);
        request.setSignName(signName);
        request.setTemplateCode(templateId);
        request.setTemplateParam(templateParams.toJSONString());
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        return sendSmsResponse;
    }

}
