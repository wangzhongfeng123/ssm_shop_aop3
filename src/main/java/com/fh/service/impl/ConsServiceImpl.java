package com.fh.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fh.dao.*;
import com.fh.intercepter.exception.CountException;
import com.fh.model.po.*;
import com.fh.service.ConsService;
import com.fh.util.AliPayUtil;
import com.fh.util.PayStatusEnum;
import com.fh.util.RedisUse;
import com.fh.util.WeiPay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

@Service
public class ConsServiceImpl implements ConsService {

    @Autowired
    private ConsDao consDao;

    @Autowired
    private VipDao vipDao;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ShopDao shopDao;

    @Autowired
    private OrderdetailDao orderdetailDao;

    @Autowired
    private OrderDao orderDao;

    //生成二维码
    @Override
    public Map selectgetCode(String orderId) throws Exception {
        Map map = new HashMap();
        String code_url = RedisUse.get("code_url");
        if (StringUtils.isEmpty(code_url)!=true){
            map.put("code",200);
            map.put("getCodeShow",code_url);
            return map;
        }
        Orderdetail order = orderdetailDao.selectById(orderId);
        String totalMoney1 = String.valueOf(order.getTotalMoney());
        Map natvie = WeiPay.natvie(orderId, totalMoney1);
        if ("SUCCESS".equalsIgnoreCase((String) natvie.get("return_code"))&&"SUCCESS".equalsIgnoreCase((String) natvie.get("result_code"))){
            map.put("code",200);
            map.put("getCodeShow",natvie.get("code_url"));
            RedisUse.set("code_url",natvie.get("code_url")+"",60*5);
            order.setPayStatus(PayStatusEnum.PAY_STATUS_ING.getStatus());
            orderdetailDao.updateById(order);
        }else {
            map.put("code",600);
            map.put("info",natvie.get("return_msg"));
        }
        return map;
    }

    @Override
    public String getZhiFuBaoFrom(String orderId) {
        String s = AliPayUtil.aliPay(orderId);
        return s;
    }

    //查询订单状态
    @Override
    public Integer selectCode(String orderId) throws Exception {
        Map<String, String> map = WeiPay.orderQuery(orderId);
        if("SUCCESS".equalsIgnoreCase(map.get("return_code"))&&"SUCCESS".equalsIgnoreCase(map.get("result_code"))){
            if("SUCCESS".equalsIgnoreCase(map.get("trade_state"))){//支付成功
                //更新订单状态
                Orderdetail order=new Orderdetail();
                order.setId(Integer.valueOf(orderId));
                order.setPayStatus(PayStatusEnum.PAY_STATUS_SUCCESS.getStatus());
                orderdetailDao.updateById(order);
                return 1;
            }else if("NOTPAY".equalsIgnoreCase(map.get("trade_state"))){
                return 3;
            }else if("USERPAYING".equalsIgnoreCase(map.get("trade_state"))){
                return 2;
            }
        }
        return 0;
    }

    //地区
    @Override
    public String selectAreaShow() {
        List<String> areas = RedisUse.hvals("t_area1");
        List<Area>stringList=new ArrayList<>();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i <areas.size() ; i++) {
            String s = areas.get(i);
            Area area = JSONObject.parseObject(s, Area.class);
            stringList.add(area);
        }
        String string = JSONObject.toJSONString(stringList);
        return string;
    }

    //新增
    @Override
    public void addShouJianParent(Cons cons) {
        Vip vip = (Vip) request.getAttribute("login_user");
        String vipName = vip.getVipName();
        List<Vip> vipList = vipDao.selectByIphone(vipName);
        if (vipList!=null){
            for (int i = 0; i <vipList.size() ; i++) {
                Vip vip1 = vipList.get(i);
                if (vip1.getVipName().equals(vipName)){
                    cons.setVipId(vip1.getVipId());
                }else if (vip1.getVipPhone().equals(vipName)){
                    cons.setVipId(vip1.getVipId());
                }
            }
            cons.setCreateDate(new Date());
            consDao.insert(cons);
        }
    }

    //展示被选中的
    @Override
    public List<Cons> selectParent(Integer nu) {
        List<Cons>consList = consDao.selectList(null);
        List<Cons>cons1 =new ArrayList<>();
        for (int i = 0; i <consList.size() ; i++) {
            Cons cons = consList.get(i);
            if (nu==null){
                if (cons.getIsCheck()==1){
                    String s = RedisUse.areaIds(cons.getAreaIds());
                    cons.setAreaIds(s+cons.getDetailAdd());
                    cons1.add(cons);
                }
            }else if (nu==2){
                if (cons.getIsCheck()==0){
                    String s = RedisUse.areaIds(cons.getAreaIds());
                    cons.setAreaIds(s+cons.getDetailAdd());
                    cons1.add(cons);
                }
            }
        }
        return cons1;
    }

    //订单信息展示
    @Override
    public List<Cart> selectCartShow() {
        Vip vip = (Vip) request.getAttribute("login_user");
        String vipName = vip.getVipName();
        List<String> hvals = RedisUse.hvals("cart_shop" + vipName);
        List<Cart> stringList= new ArrayList<>();
        for (int i = 0; i <hvals.size() ; i++) {
            String s = hvals.get(i);
            Cart cart = JSONObject.parseObject(s, Cart.class);
            if (cart.isCheck()==true){
                stringList.add(cart);
            }
        }
        return stringList;
    }

    //修改收件人
    @Override
    public void updateParent(Integer m) {
        List<Cons> cons = consDao.selectList(null);
        for (int i = 0; i <cons.size() ; i++) {
            Cons cons1 = cons.get(i);
            if (cons1.getId()==m){
                consDao.updateByCheck(m);
            }
            if (cons1.getIsCheck()==1){
                consDao.updateByChecked(cons1.getId());
            }
        }
    }

    //提交订单
    @Override
    public Map addFrom(Integer shopId, Integer val) throws CountException {
        Vip vip = (Vip) request.getAttribute("login_user");
        String vipName = vip.getVipName();
        Orderdetail orderdetail=new Orderdetail();
        List<Order> orderList=new ArrayList<>();
        List<Vip> vipList = vipDao.selectByIphone(vipName);
        for (int i = 0; i <vipList.size() ; i++) {
            Vip vip1 = vipList.get(i);
            if (vip1.getVipPhone().equals(vipName)){
                orderdetail.setVipId(vip1.getVipId());
            }
            if (vip1.getVipName().equals(vipName)){
                orderdetail.setVipId(vip1.getVipId());
            }
        }
        orderdetail.setAddressId(shopId);
        orderdetail.setPayType(val);
        orderdetail.setCreateDate(new Date());
        orderdetail.setPayStatus(PayStatusEnum.PAY_STATUS_INIT.getStatus());
        Integer count = 0;
        BigDecimal bigDecimal = new BigDecimal(0);
        List<String> hvals = RedisUse.hvals("cart_shop" + vipName);
        for (int i = 0; i <hvals.size() ; i++) {
            Cart cart = JSONObject.parseObject(hvals.get(i), Cart.class);
            if (StringUtils.isEmpty(cart)!=true){
                if(cart.isCheck()==true){
                    Shop shop = shopDao.selectById(cart.getShopId());
                    if (cart.getCount()<=shop.getStoryCount()){
                        count++;
                        BigDecimal money = cart.getMoney();
                        bigDecimal = bigDecimal.add(money);
                        Order order = new Order();
                        order.setCount(cart.getCount());
                        order.setProductId(cart.getShopId());
                        orderList.add(order);
                        int s = shopDao.updateByCount(shop.getShopId(),cart.getCount());
                        if (s==0){
                            throw new CountException(shop.getShopId()+","+"商品名为:"+cart.getShopName()+"的库存不足 库存为："+shop.getStoryCount());
                        }
                    }else {
                        throw new CountException(shop.getShopId()+","+"商品名为:"+cart.getShopName()+"的库存不足 库存为："+shop.getStoryCount());
                    }
                }
            }
        }
        orderdetail.setProTypeCount(count);
        orderdetail.setTotalMoney(bigDecimal);
        orderdetailDao.insert(orderdetail);
        orderDao.addBatch(orderList,orderdetail.getId());
        for (int i = 0; i <hvals.size() ; i++) {
            Cart cart = JSONObject.parseObject(hvals.get(i), Cart.class);
            RedisUse.hdel("cart_" + vipName + "_shop",cart.getShopId()+"");
        }
        Map map = new HashMap();
        map.put("orderId",orderdetail.getId());
        map.put("totalMoney",bigDecimal);
        return map;
    }

    //修改数量
    @Override
    public void updateCount(Integer shopId, Integer count) {
        Vip vip = (Vip) request.getAttribute("login_user");
        String vipName = vip.getVipName();
        String hget = RedisUse.hget("cart_shop" + vipName, shopId + "");
        if (StringUtils.isEmpty(hget)!=true){
            Cart cart = JSONObject.parseObject(hget, Cart.class);
            cart.setCount(count);
            cart.setMoney(new BigDecimal(count).multiply(new BigDecimal(String.valueOf(cart.getPrice()))));
            RedisUse.hset("cart_shop" + vipName, shopId + "",JSONObject.toJSONString(cart));
        }
    }
}
