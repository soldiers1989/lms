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
import java.util.Date;

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
    @AdminOnly
    @RequestMapping("/create")
    public String createWardrobe(@RequestBody Wardrobe wardrobe) {
        wardrobe.setCreateTime(LocalDateTime.now());
        wardrobe.setCreator(getId());
        return super.getSuccessResult(wardrobeService.save(wardrobe));
    }

    /**
     * 激活或者停用
     *
     * @param wardrobeId
     * @param activate
     * @return
     */
    @AdminAndLaundry
    @RequestMapping("/activate")
    public String activateWardrobe(@RequestParam(name = "wardrobeId") int wardrobeId,
                                   @RequestParam(name = "activate") boolean activate) {
        if (!isAdminOrLaundry()) {
            return noAuth();
        }
        Wardrobe wardrobe = wardrobeService.getById(wardrobeId);
        if (wardrobe.getActivated() == !activate) {
            return wrongState();
        }
        wardrobe.setModifyTime(LocalDateTime.now());
        wardrobe.setActivated(true);
        wardrobe.setModifier(getId());
        return super.getSuccessResult(wardrobeService.saveOrUpdate(wardrobe));
    }

    //2.获取柜子列表
    @AdminAndLaundry
    @RequestMapping("/select")
    public String selectByUserId(
            @RequestParam(name = PAGE_NUM_KEY) int pageNum,
            @RequestParam(name = PAGE_SIZE_KEY) int pageSize) {
        QueryWrapper<Wardrobe> wardrobeQueryWrapper = new QueryWrapper<>();
        wardrobeQueryWrapper.eq("deleted", 0);
        if (isLaundry()) {
            wardrobeQueryWrapper.eq("laundry_id", getUser().getLaundryId());
        } else if (isAdmin()) {

        } else {
            return noAuth();
        }
        return super.getSuccessPage(wardrobeService.page(new Page(pageNum, pageSize), wardrobeQueryWrapper));
    }

    //2.获取可用的柜子列表
    @LoginOnly
    @RequestMapping("/selectNearest")
    public String selectNearest(
            @RequestParam(name = KEY_WORD_KEY, required = false, defaultValue = "") String keyWord,
            @RequestParam(name = PAGE_NUM_KEY, required = false, defaultValue = "1") int pageNum,
            @RequestParam(name = PAGE_SIZE_KEY, required = false, defaultValue = "200000") int pageSize) {
        QueryWrapper<Wardrobe> wardrobeQueryWrapper = new QueryWrapper<>();
        wardrobeQueryWrapper.eq("deleted", 0);
        wardrobeQueryWrapper.eq("activated", 1);
        if (!isLogin()) {
            return noAuth();
        }
        if (StringUtils.isNotEmpty(keyWord)) {
            wardrobeQueryWrapper.like("address", keyWord);
        }
        return super.getSuccessPage(wardrobeService.page(new Page(pageNum, pageSize), wardrobeQueryWrapper));
    }

    //更新柜子信息
    @AdminAndLaundry
    @RequestMapping("/update")
    public String update(@RequestBody Wardrobe wardrobe) {
        Wardrobe fromDBWardrobe = wardrobeService.getById(wardrobe.getId());
        //部分信息洗衣店没有权限更改
        if (isLaundry()) {
            wardrobe.setCreator(fromDBWardrobe.getCreator());
            wardrobe.setLaundryId(fromDBWardrobe.getLaundryId());
            wardrobe.setCreateTime(fromDBWardrobe.getCreateTime());
            wardrobe.setSwVersion(fromDBWardrobe.getSwVersion());
        } else if (isAdmin()) {

        } else {
            return noAuth();
        }
        wardrobe.setModifier(getId());
        wardrobe.setModifyTime(LocalDateTime.now());
        return getSuccessResult(wardrobeService.saveOrUpdate(wardrobe));
    }

    //柜子报障
    @LoginOnly
    @RequestMapping("/problem/create")
    public String submitProblem(@RequestBody WardrobeProblem wardrobeProblem) {
        wardrobeProblem.setCreateTime(new Date());
        wardrobeProblem.setUserId(getId());
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


}
