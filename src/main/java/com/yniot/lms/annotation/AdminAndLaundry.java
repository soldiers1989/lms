package com.yniot.lms.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
/**
 * 标记方法或类只允许管理员或者洗衣店身份使用
 */
public @interface AdminAndLaundry {
}
