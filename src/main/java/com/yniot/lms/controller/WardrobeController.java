package com.yniot.lms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yniot.lms.annotation.AdminAndLaundry;
import com.yniot.lms.annotation.AdminOnly;
import com.yniot.lms.annotation.LoginOnly;
import com.yniot.lms.controller.commonController.BaseControllerT;
import com.yniot.lms.db.entity.Wardrobe;
import com.yniot.lms.db.entity.WardrobeProblem;
import com.yniot.lms.service.WardrobeProblemService;
import com.yniot.lms.service.WardrobeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther: lane
 * @Date: 2018/11/19 08:49
 * @Description:
 * @Version 1.0.0
 */
@RequestMapping("/wardrobe")
@RestController
public class WardrobeController extends BaseControllerT<Wardrobe> {

    @Autowired
    WardrobeService wardrobeService;
    @Autowired
    WardrobeProblemService wardrobeProblemService;

    //1.创建柜子
//    @AdminOnly
//    @RequestMapping("/create")
//    public String createWardrobe(@RequestBody Wardrobe wardrobe) {
//        wardrobe.setCreateTime(LocalDateTime.now());
//        wardrobe.setCreator(getUserId());
//        return super.getSuccessResult(wardrobeService.save(wardrobe));
//    }

    @AdminOnly
    @RequestMapping("/insert")
    public String insert(@RequestBody Wardrobe wardrobe) {
        if (StringUtils.isNotEmpty(wardrobe.getWardrobeCode()) && wardrobeService.exists(wardrobe.getWardrobeCode())) {
            return getErrorMsg("编号已存在!");
        }
        wardrobe.setActivated(false);
        wardrobe.setCreateTime(LocalDateTime.now());
        wardrobe.setCreator(getUserId());
        return super.getSuccessResult(wardrobeService.createWardrobe(wardrobe));
    }


    @AdminOnly
    @RequestMapping("/relateLaundry")
    public String relateLaundry(@RequestParam boolean relate,
                                @RequestParam int laundryId,
                                @RequestParam(value = "wardrobeId") int wardrobeId) {
        List<Integer> wardrobeIdList = new ArrayList<>();
        wardrobeIdList.add(wardrobeId);
        return getSuccessResult(wardrobeService.relateLaundry(relate, laundryId, wardrobeIdList));
    }

    //2.获取柜子列表
    @AdminAndLaundry
    @RequestMapping("/select")
    public String select(
            @RequestParam(name = "keyWord") String keyWord,
            @RequestParam(name = "laundryId", required = false, defaultValue = "0") int laundryId,
            @RequestParam(name = PAGE_NUM_KEY) int pageNum,
            @RequestParam(name = PAGE_SIZE_KEY) int pageSize) {
        QueryWrapper<Wardrobe> wardrobeQueryWrapper = new QueryWrapper<>();
        wardrobeQueryWrapper.eq("deleted", 0);
//        if (StringUtils.isNotEmpty(keyWord)) {///&& laundryId == 0
//            wardrobeQueryWrapper.like("laundry_phone", keyWord).or()
//                    .like("laundry_name", keyWord).or()
//                    .like("address", keyWord).or()
//                    .like("wardrobe_code", keyWord);
//        }
        if (isAdmin()) {
        } else if (hasLaundry()) {
            if (laundryId > 0) {
                wardrobeQueryWrapper.eq("laundry_id", laundryId);
            } else {
                wardrobeQueryWrapper.in("laundry_id", getLaundryIdList());
            }

        } else {
            return noAuth();
        }
        return super.getSuccessPage(wardrobeService.page(new Page(pageNum, pageSize), wardrobeQueryWrapper));
    }

    @AdminOnly
    @RequestMapping("/selectForRelate")
    public String selectForRelate(
            @RequestParam(name = "laundryId", required = false, defaultValue = "0") int laundryId,
            @RequestParam(name = "keyWord") String keyWord,
            @RequestParam(name = PAGE_NUM_KEY) int pageNum,
            @RequestParam(name = PAGE_SIZE_KEY) int pageSize) {
        if (!isAdmin()) {
            return noAuth();
        }
        QueryWrapper<Wardrobe> wardrobeQueryWrapper = new QueryWrapper<>();
//        QueryWrapper<Wardrobe> commonQueryWrapper = new QueryWrapper<>();

        wardrobeQueryWrapper.isNull("laundry_id").or().eq("laundry_id", laundryId);
//        if (StringUtils.isNotEmpty(keyWord)) {///&& laundryId == 0
//            commonQueryWrapper.like("laundry_phone", keyWord).or()
//                    .like("laundry_name", keyWord).or()
//                    .like("address", keyWord).or()
//                    .like("wardrobe_code", keyWord);
//        }

        return super.getSuccessPage(wardrobeService.page(new Page(pageNum, pageSize), wardrobeQueryWrapper));
    }


    //2.获取可用的柜子列表
    @LoginOnly
    @RequestMapping("/selectNearest")
    public String selectNearest(
            @RequestParam(name = KEY_WORD_KEY, required = false, defaultValue = "") String keyWord,
            @RequestParam(name = PAGE_NUM_KEY, required = false, defaultValue = "1") int pageNum,
            @RequestParam(name = PAGE_SIZE_KEY, required = false, defaultValue = "-1") int pageSize) {
        QueryWrapper<Wardrobe> wardrobeQueryWrapper = new QueryWrapper<>();
        wardrobeQueryWrapper.eq("deleted", 0);
        wardrobeQueryWrapper.eq("activated", 1);
        if (!isLogin()) {
            return noAuth();
        }
        if (StringUtils.isNotEmpty(keyWord)) {
            wardrobeQueryWrapper.like("address", keyWord);
        }
        if (pageSize > 0) {
            return super.getSuccessPage(wardrobeService.page(new Page(pageNum, pageSize), wardrobeQueryWrapper));
        } else {
            return super.getSuccessResult(wardrobeService.list(wardrobeQueryWrapper));
        }
    }

    //更新柜子信息
    @AdminAndLaundry
    @RequestMapping("/update")
    public String update(@RequestBody Wardrobe wardrobe) {
        Wardrobe fromDBWardrobe = wardrobeService.getById(wardrobe.getId());
        //部分信息洗衣店没有权限更改
        if (hasLaundry()) {
            wardrobe.setCreator(fromDBWardrobe.getCreator());
            wardrobe.setLaundryId(fromDBWardrobe.getLaundryId());
            wardrobe.setCreateTime(fromDBWardrobe.getCreateTime());
            wardrobe.setSwVersion(fromDBWardrobe.getSwVersion());
        } else if (isAdmin()) {

        } else {
            return noAuth();
        }
        wardrobe.setModifier(getUserId());
        wardrobe.setModifyTime(LocalDateTime.now());
        return getSuccessResult(wardrobeService.saveOrUpdate(wardrobe));
    }

    //柜子报障
    @LoginOnly
    @RequestMapping("/problem/create")
    public String submitProblem(@RequestBody WardrobeProblem wardrobeProblem) {
        wardrobeProblem.setCreateTime(new Date());
        wardrobeProblem.setUserId(getUserId());
        return getSuccessResult(wardrobeProblemService.save(wardrobeProblem));
    }


    //获取报障反馈
    @AdminOnly
    @RequestMapping("/problem/select")
    public String selectProblem(@RequestParam(name = KEY_WORD_KEY) String keyWord,
                                @RequestParam(name = PAGE_NUM_KEY) int pageNum,
                                @RequestParam(name = PAGE_SIZE_KEY) int pageSize) {
        QueryWrapper<WardrobeProblem> wardrobeProblemQueryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(keyWord)) {
            wardrobeProblemQueryWrapper.like("description", keyWord).or().like("wardrobe_id", keyWord);
        }
        return getSuccessPage(wardrobeProblemService.page(new Page(pageNum, pageSize), wardrobeProblemQueryWrapper));
    }


    @RequestMapping("/activate")
    public String activate(@RequestParam boolean activate, @RequestParam(name = "wardrobeIdList[]") List<Integer> wardrobeIdList) {
        return getSuccessResult(wardrobeService.activate(activate, wardrobeIdList));
    }


    @RequestMapping("/checkCode")
    public String checkCode(@RequestParam String wardrobeCode) {
        if (StringUtils.isNotEmpty(wardrobeCode)) {
            QueryWrapper<Wardrobe> wardrobeQueryWrapper = new QueryWrapper<>();
            wardrobeQueryWrapper.eq("wardrobe_code", wardrobeCode);
            List<Wardrobe> wardrobeIdList = wardrobeService.list(wardrobeQueryWrapper);
            return getSuccessResult(wardrobeIdList == null ? 0 : wardrobeIdList.size());
        }
        return getSuccessResult(0);
    }

}
