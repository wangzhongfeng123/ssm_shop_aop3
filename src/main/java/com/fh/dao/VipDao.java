package com.fh.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.model.po.Vip;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VipDao extends BaseMapper<Vip> {

    Vip selectByVipName(String vipName);

    List<Vip> selectByIphone(@Param("iphone") String iphone);
}
