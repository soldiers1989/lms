package com.yniot.lms.security;

import org.apache.shiro.session.mgt.SessionKey;

import java.io.Serializable;

/**
 * @Auther: lane
 * @Date: 2018/12/4 17:21
 * @Description:
 * @Version 1.0.0
 */
public class CustomSessionKey implements SessionKey {
    private String sessionKey = null;

    public CustomSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    @Override
    public Serializable getSessionId() {


        return sessionKey;
    }
}
