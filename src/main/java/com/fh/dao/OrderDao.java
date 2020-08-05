package com.fh.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.model.po.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDao extends BaseMapper<Order> {
    void addBatch(@Param("orderList") List<Order> orderList,@Param("id") Integer id);
}
