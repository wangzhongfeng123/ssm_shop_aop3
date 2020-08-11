package com.fh.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fh.dao.*;
import com.fh.model.po.*;
import com.fh.model.vo.CartShow;
import com.fh.service.CartService;
import com.fh.util.RedisUse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartDao cartDao;
    @Autowired
    private ShopDao shopDao;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private OrderdetailDao orderdetailDao;
    @Autowired
    private VipDao vipDao;
    @Autowired
    private ConsDao consDao;




    //加入购物车
    @Override
    public long addCar(Cart cart) {
        Shop shop = shopDao.selectById(cart.getShopId());
        if (StringUtils.isEmpty(shop)!=true){
            if (cart.getCount()>shop.getStoryCount()){
                return cart.getCount()-shop.getStoryCount();
            }
        }
        Vip vip = (Vip) request.getAttribute("login_user");
        String vipName = vip.getVipName();
        String hget = RedisUse.hget("cart_shop" + vipName, cart.getShopId() + "");
        if (StringUtils.isEmpty(hget)){
                cart.setCheck(true);
                cart.setCount(cart.getCount());
                cart.setPhotograph(shop.getPhotograph());
                cart.setPrice(BigDecimal.valueOf(shop.getPrice()));
                cart.setShopName(shop.getShopName());
                BigDecimal bigDecimal = new BigDecimal(cart.getCount());
                BigDecimal multiply = bigDecimal.multiply(new BigDecimal(shop.getPrice()));
                cart.setMoney(multiply);
                cart.setShopId(shop.getShopId());
                String string = JSONObject.toJSONString(cart);
                RedisUse.hset("cart_shop" + vipName, cart.getShopId()+"",string);
        }else{
            Cart cart1 = JSONObject.parseObject(hget, Cart.class);
            cart1.setCount(cart1.getCount()+cart.getCount());
            BigDecimal bigDecimal = new BigDecimal(cart1.getCount()+cart.getCount());
            BigDecimal multiply = bigDecimal.multiply(new BigDecimal(shop.getPrice()));
            cart1.setMoney(multiply);
            String string = JSONObject.toJSONString(cart1);
            RedisUse.hset("cart_shop" + vipName, cart.getShopId()+"",string);
        }
        long hlen = RedisUse.hlen("cart_shop" + vipName);
        return hlen;
    }
    //购物车展示
    @Override
    public List<String> selectCart() {
        Vip vip = (Vip) request.getAttribute("login_user");
        String vipName = vip.getVipName();
        List<String> hvals = RedisUse.hvals("cart_shop" + vipName);
        List<String> cartList = new ArrayList<>();
        if (StringUtils.isEmpty(hvals)!=true){
            for (int i = 0; i <hvals.size() ; i++) {
                String s = hvals.get(i);
                Cart cart = new Cart();
                Cart cart1 = JSONObject.parseObject(s, Cart.class);
                cart.setShopId(cart1.getShopId());
                cart.setMoney(cart1.getMoney());
                cart.setCount(cart1.getCount());
                cart.setShopName(cart1.getShopName());
                cart.setPrice(cart1.getPrice());
                cart.setPhotograph(cart1.getPhotograph());
                cart.setCheck(cart1.isCheck());
                String string = JSONObject.toJSONString(cart);
                cartList.add(string);
            }
        }

        return cartList;
    }
    //加减购物车，减1加2
    @Override
    public void updateCart(Integer shopId, Integer nu, Integer count) {
        Vip vip = (Vip) request.getAttribute("login_user");
        String vipName = vip.getVipName();
        String hget = RedisUse.hget("cart_shop" + vipName, shopId + "");
        if (StringUtils.isEmpty(hget)!=true){
            Cart cart = JSONObject.parseObject(hget, Cart.class);
            if (nu==1){
                if (count==0){
                    cart.setCount(0);
                    cart.setMoney(new BigDecimal(0));
                }else {
                    cart.setCount(cart.getCount()-1);
                    BigDecimal bigDecimal = new BigDecimal(cart.getCount());
                    BigDecimal multiply = bigDecimal.multiply(cart.getPrice());
                    cart.setMoney(multiply);
                }
            }else if (nu==2){
                Shop shop = shopDao.selectById(shopId);
                if (shop.getStoryCount()==count){
                    cart.setCount(shop.getStoryCount());
                    cart.setMoney(new BigDecimal(shop.getStoryCount()).multiply(new BigDecimal(String.valueOf(cart.getPrice()))));
                }else {
                    cart.setCount(cart.getCount()+1);
                    BigDecimal bigDecimal = new BigDecimal(cart.getCount());
                    BigDecimal multiply = bigDecimal.multiply(cart.getPrice());
                    cart.setMoney(multiply);
                }

            }
            String string = JSONObject.toJSONString(cart);
            RedisUse.hset("cart_shop" + vipName, shopId + "",string);
        }
    }
    //批量删除购物车中的商品
    @Override
    public void deleteAll(List<Integer> str) {
        Vip vip = (Vip) request.getAttribute("login_user");
        String vipName = vip.getVipName();
        for (int i = 0; i <str.size() ; i++) {
            Integer integer = str.get(i);
            RedisUse.hdel("cart_shop" + vipName,integer+"");
        }
    }
    //单删
    @Override
    public void deleteShop(Integer shopId) {
        Vip vip = (Vip) request.getAttribute("login_user");
        String vipName = vip.getVipName();
        RedisUse.hdel("cart_shop" + vipName,shopId+"");
    }
    //修改复选框选中状态
    @Override
    public void updateChecked(String str ,Integer shopId) {
        Vip vip = (Vip) request.getAttribute("login_user");
        String vipName = vip.getVipName();
        long hlen = RedisUse.hlen("cart_shop" + vipName);
        if (str!=null && str!=""){
            String[] split = str.split(",");
            int count = 0;
            Cart cart =null;
            for (int i = 0; i <split.length ; i++) {
                Integer id = Integer.valueOf(split[i]);
                String hget = RedisUse.hget("cart_shop" + vipName, JSONObject.toJSONString(id));
                cart = JSONObject.parseObject(hget, Cart.class);
                if (cart.isCheck()==true){
                    count++;
                }
            }
            if (hlen!=count){
                Cart cart1= null;
                for (int i = 0; i <split.length ; i++) {
                    Integer id = Integer.valueOf(split[i]);
                    String hget = RedisUse.hget("cart_shop" + vipName, JSONObject.toJSONString(id));
                    cart1 = JSONObject.parseObject(hget, Cart.class);
                    cart1.setCheck(true);
                    RedisUse.hset("cart_shop" + vipName,cart1.getShopId()+"",JSONObject.toJSONString(cart1));
                }
            } else {
                Cart cart2= null;
                for (int i = 0; i <split.length ; i++) {
                    Integer ids = Integer.valueOf(split[i]);
                    String hget = RedisUse.hget("cart_shop" + vipName, JSONObject.toJSONString(ids));
                    cart2 = JSONObject.parseObject(hget, Cart.class);
                    cart2.setCheck(false);
                    RedisUse.hset("cart_shop" + vipName,cart2.getShopId()+"",JSONObject.toJSONString(cart2));
                }
            }
        }else{
            if (shopId!=null){
                String hget = RedisUse.hget("cart_shop" + vipName, shopId + "");
                Cart cart = JSONObject.parseObject(hget, Cart.class);
                if (cart.isCheck()==true){
                    cart.setCheck(false);
                }else {
                    cart.setCheck(true);
                }
                RedisUse.hset("cart_shop" + vipName, shopId + "",JSONObject.toJSONString(cart));
            }
        }
    }
    //
    @Override
    public List<CartShow> selectCartShow() {
        List<Orderdetail> orderList1 = orderdetailDao.selectList(null);
        List<CartShow> list = new ArrayList();
        for (int i = 0; i <orderList1.size() ; i++) {
            CartShow cartShow = new CartShow();
            Orderdetail orderdetail = orderList1.get(i);
            Integer addressId = orderdetail.getAddressId();
            Cons shParent = consDao.selectById(addressId);
            String areaIds = shParent.getAreaIds();
            String s = RedisUse.areaIds(areaIds);
            cartShow.setAreaIds(s+shParent.getDetailAdd());
            cartShow.setCreateDate(orderdetail.getCreateDate());
            cartShow.setPayType(orderdetail.getPayType());
            cartShow.setProTypeCount(orderdetail.getProTypeCount());
            cartShow.setId(orderdetail.getId());
            Vip vip = vipDao.selectById(orderdetail.getVipId());
            cartShow.setVipId(vip.getVipName());
            cartShow.setPayStatus(orderdetail.getPayStatus());
            cartShow.setTotalMoney(orderdetail.getTotalMoney());
            list.add(cartShow);
        }
        return list;
    }

    @Override
    public void updateCartByType(Integer id) {
        Orderdetail order = orderdetailDao.selectById(id);
        Integer payType = order.getPayType();
        if (payType==0){
            order.setPayType(1);
            orderdetailDao.updateById(order);
        }else if (payType==1){
            order.setPayType(0);
            orderdetailDao.updateById(order);
        }else if (payType==null){
            orderdetailDao.updateById(order);
        }
    }
}
