package com.yniot.lms.annotation;

import java.lang.annotation.*;

/**
 * @Author wanggl
 * @Description 完成标注,只作提醒,对程序无实际影响
 * @create 15:57 2018-11-22
 **/
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Finished {
}
