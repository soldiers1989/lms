package com.yniot.lms.db.dao;

import com.yniot.exclude.CommonMapper;
import com.yniot.lms.db.entity.OrderShipment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface OrderShipmentMapper extends CommonMapper<OrderShipment> {


    @Update("update biz_order_shipment set state = #{state} where order_id= #{orderId}")
    int updateState(int orderId, int state);


    //暂时不加状态条件
    @Update("update biz_order_shipment set password = FLOOR(0 + (RAND() * 999999 )) ," +
            "psw_expire_time = date_add(sysdate(), INTERVAL #{expireMin} MINUTE) " +
            "where  sysdate()>psw_expire_time ")
    void updatePassword(int expireMin);

    @Select("select count(1) from biz_order_shipment where password=#{password} and wardrobe_id =#{wardrobeId} and cell_id=#{cellId}")
    int checkPassword(int wardrobeId, int cellId, String password);
}