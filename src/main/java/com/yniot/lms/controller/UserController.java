package com.yniot.lms.controller;

import com.yniot.lms.controller.commonController.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController extends BaseController {
    @RequestMapping(name="/test")
    public String test(){
        return super.getSuccessResult("hello");
    }

}
