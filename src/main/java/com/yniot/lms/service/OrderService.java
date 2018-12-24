package com.yniot.lms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.Page;
import com.yniot.lms.db.entity.Order;
import me.chanjar.weixin.common.error.WxErrorException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @project: lms
 * @description:
 * @author: wanggl
 * @create: 2018-11-22 13:52
 **/
public interface OrderService extends IService<Order> {

    int markExpiredOrder();

    int EXPIRED_IN_MIN = 10;

    Order getByOrderCode(String orderNo);

    boolean expiredOrder(int orderId);

    boolean updateState(int orderId, int state);

    boolean generateOrder(int userId, String appId, int wardrobeId, String description) throws WxErrorException;

    boolean removeByCode(String orderCode);

    boolean receiveOrder(int userId, int orderId);

    IPage<Order> selectByLaundryId(int pageNum, int pageSize, int laundryId, String keyWord, List<Integer> stateList);

    boolean paid_procedure( String orderCode, BigDecimal price, String transactionId, String tradeType);

    boolean paid_procedure( int orderId, BigDecimal price, String transactionId, String tradeType);

    int startCleaning(List<Integer> orderIdList);

    int cleaned(List<Integer> orderIdList);

    int send(List<Integer> orderIdList);

    Page getFullDetail(List<Integer> orderIdList, int pageNum, int pageSize);

    List<Map<String, Integer>> getStatisticInfo(int laundryId);

}
