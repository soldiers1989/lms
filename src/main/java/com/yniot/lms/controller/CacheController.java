package com.yniot.lms.controller;

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
@RestController
public class CacheController extends BaseController {


    @Autowired
    private CacheDao cacheDao;

    @RequestMapping(value = "cache/set", produces = {"application/json;charset=UTF-8"})
    public String setCache(@RequestParam(name = "key") String key,
                           @RequestParam(name = "value") String value) {
        cacheDao.set(key, value);
        return super.getSuccessResult(1);
    }

    @RequestMapping(value = "cache/get", produces = {"application/json;charset=UTF-8"})
    public String getCache(@RequestParam(name = "key") String key) {
        return super.getSuccessResult(cacheDao.get(key));
    }

    @RequestMapping(value = "cache/num", produces = {"application/json;charset=UTF-8"})
    public String getCacheNum(@RequestParam(name = "pattern") String pattern) {
        return super.getSuccessResult(cacheDao.getKeys(pattern));
    }

    @RequestMapping(value = "cache/delete", produces = {"application/json;charset=UTF-8"})
    public String deleteCache(@RequestParam(name = "key") String key) {
        if ("*".equals(key)) {
            return super.getSuccessResult(cacheDao.deleteAll());
        } else {
            return super.getSuccessResult(cacheDao.delete(key));
        }
    }

}
