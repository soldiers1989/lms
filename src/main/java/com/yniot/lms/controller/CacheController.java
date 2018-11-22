package com.yniot.lms.controller;

import com.yniot.lms.annotation.Anonymous;
import com.yniot.lms.controller.commonController.BaseController;
import com.yniot.lms.db.cachce.CacheDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:wanggl
 * @date:2018-10-16
 * @version:1.0.0
 */
@Anonymous
@RestController
@RequestMapping(name = "/cache", produces = {"application/json;charset=UTF-8"})
public class CacheController extends BaseController {


    @Autowired
    private CacheDao cacheDao;

    @RequestMapping("/set")
    public String setCache(@RequestParam(name = "key") String key,
                           @RequestParam(name = "value") String value) {
        cacheDao.set(key, value);
        return super.getSuccessResult(1);
    }

    @RequestMapping("/get")
    public String getCache(@RequestParam(name = "key") String key) {
        return super.getSuccessResult(cacheDao.get(key));
    }

    @RequestMapping("/num")
    public String getCacheNum(@RequestParam(name = "pattern") String pattern) {
        return super.getSuccessResult(cacheDao.getKeys(pattern));
    }

    @RequestMapping("/delete")
    public String deleteCache(@RequestParam(name = "key") String key) {
        if ("*".equals(key)) {
            return super.getSuccessResult(cacheDao.deleteAll());
        } else {
            return super.getSuccessResult(cacheDao.delete(key));
        }
    }

}
