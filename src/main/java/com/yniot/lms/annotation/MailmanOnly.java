package com.yniot.lms.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
/**
 * 标记方法或类只允许配送员身份使用
 */
public @interface MailmanOnly {
}
