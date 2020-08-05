package com.fh.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fh.dao.ShopDao;
import com.fh.model.po.Area;
import com.fh.model.po.Shop;
import com.fh.model.vo.ShopVo;
import com.fh.service.ShopService;
import com.fh.util.RedisUse;
import com.fh.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopDao shopDao;

    //热销
    @Override
    public String selectShop() {
        String t_shop_wzf = RedisUse.get("t_shop_wzf");
        if (StringUtils.isEmpty(t_shop_wzf)==true){
            List<Shop> shopList = shopDao.selectList(null);
            List<Shop>shopList1=new ArrayList<>();
            for (int i = 0; i <shopList.size() ; i++) {
                Shop shop = shopList.get(i);
                if (shop.getShopCakes()==1){
                    shopList1.add(shop);
                }
            }
            String string = JSONObject.toJSONString(shopList1);
            RedisUse.set("t_shop_wzf",string);
            String t_shop_wzf1 = RedisUse.get("t_shop_wzf");
            return t_shop_wzf1;
        }
        return t_shop_wzf;
    }
    //全部商品
    @Override
    public String selectBacthShop(Integer typeId) {
        String t_shop_show = RedisUse.get("t_shop_show");
        if (StringUtils.isEmpty(t_shop_show)==true){
            List<Shop> shopList = shopDao.selectList(null);
            List<ShopVo>shopVoList=new ArrayList<>();
            for (int i = 0; i <shopList.size() ; i++) {
                Shop shop = shopList.get(i);
                if (shop.getType()==typeId){
                    ShopVo shopVo = new ShopVo();
                    shopVo.setShopId(shop.getShopId());
                    shopVo.setPhotograph(shop.getPhotograph());
                    shopVo.setShopName(shop.getShopName());
                    shopVo.setPrice(shop.getPrice());
                    shopVo.setId(shop.getShopId());
                    shopVoList.add(shopVo);
                }
            }
            String string = JSONObject.toJSONString(shopVoList);
            RedisUse.set("t_shop_show",string);
            String t_shop_show1 = RedisUse.get("t_shop_show");
            return t_shop_show1;
        }
        return t_shop_show;
    }
    //根据id查询商品
    @Override
    public ShopVo selectShopById(Integer shopId) {
        Shop shop = shopDao.selectById(shopId);
        String area = shop.getArea();
        String s = RedisUse.areaIds(area);
        ShopVo shopVo = new ShopVo();
        shopVo.setShopName(shop.getShopName());
        shopVo.setPhotograph(shop.getPhotograph());
        shopVo.setArea(s);
        shopVo.setPrice(shop.getPrice());
        return shopVo;
    }
}
