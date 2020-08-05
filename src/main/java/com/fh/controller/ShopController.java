package com.fh.controller;

import com.fh.model.po.Shop;
import com.fh.model.vo.ShopVo;
import com.fh.service.ShopService;
import com.fh.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("shop")
@RestController
public class ShopController {

    @Autowired
    private ShopService shopService;

    //根据id查询商品
    @RequestMapping("selectShopById")
    public JsonData selectShopById(Integer shopId){
        ShopVo shop = shopService.selectShopById(shopId);
        return JsonData.getJsonSuccess(shop);
    }

    //热销商品
    @RequestMapping("selectShop")
    public JsonData selectShop(){
        String shopList = shopService.selectShop();
        return JsonData.getJsonSuccess(shopList);
    }

    //全部商品
    @RequestMapping("selectShopByType")
    public JsonData selectBacthShop(Integer typeId){
        String shop = shopService.selectBacthShop(typeId);
        return JsonData.getJsonSuccess(shop);
    }

}
