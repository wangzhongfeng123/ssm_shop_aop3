package com.fh.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.model.po.Shop;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopDao extends BaseMapper<Shop> {

    int updateByCount(@Param("shopId") Integer shopId,@Param("count") Integer count);
}
