package com.yniot.lms.db.interceptor;

import com.yniot.lms.db.cachce.CacheDao;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

/**
 * mybatis拦截器,用于拦截update/delete操作并更新缓存
 * @author:wanggl
 * @date:2018-10-18
 * @version:1.0.0
 */
@Intercepts({
        @Signature(type = Executor.class,
                method = "update",
                args = {MappedStatement.class, Object.class})})
public class ModifyInterceptor implements Interceptor {
    private static Logger logger = Logger.getLogger(ModifyInterceptor.class);

    @Autowired
    CacheDao cacheDao;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        return this.afterHandler(invocation);
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }


    /**
     * @param invocation
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private Object afterHandler(Invocation invocation) throws InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        return invocation.proceed();
    }




}
