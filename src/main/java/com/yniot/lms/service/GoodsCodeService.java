package com.yniot.lms.service;

import com.yniot.lms.db.entity.GoodsCode;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wanggl
 * @since 2018-12-06
 */
public interface GoodsCodeService extends IService<GoodsCode> {
    int isExists(String code);

    int relateCode(List<String> codeList, int orderGoodsId);


    int releaseCode(List<String> codeList);

    List<GoodsCode> getByOrderGoodsId(int orderGoodsId);

    List<GoodsCode> getByOrderGoodsId(List<Integer> orderGoodsIdList);

}
