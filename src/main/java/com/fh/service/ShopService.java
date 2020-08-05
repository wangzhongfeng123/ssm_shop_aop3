package com.fh.service;

import com.fh.model.vo.ShopVo;

public interface ShopService {
    String selectShop();

    String selectBacthShop(Integer typeId);

    ShopVo selectShopById(Integer shopId);
}
