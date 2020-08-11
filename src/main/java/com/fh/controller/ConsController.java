package com.fh.controller;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.fh.dao.ConsDao;
import com.fh.intercepter.exception.CountException;
import com.fh.model.po.Cart;
import com.fh.model.po.Cons;
import com.fh.service.ConsService;
import com.fh.util.AliPayUtil;
import com.fh.util.JsonData;
import com.fh.util.RedisUse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("cons")
public class ConsController {


    @Autowired
    private ConsService consService;


    //查询支付宝支付状态
    @RequestMapping("selectZhiFuBaoFrom")
    public JsonData selectZhiFuBaoFrom(String orderId) throws AlipayApiException {
        AliPayUtil.queryAliPayStatus(orderId);
        return JsonData.getJsonSuccess("");
    }

    //支付宝
    @RequestMapping("getZhiFuBaoFrom")
    public JsonData getZhiFuBaoFrom(String orderId){
        String from = consService.getZhiFuBaoFrom(orderId);
        return JsonData.getJsonSuccess(from);
    }


    //获取支付状态码
    @RequestMapping("getCodeCode")
    public JsonData selectCode(String orderId) throws Exception {
        Integer integer = consService.selectCode(orderId);
        return JsonData.getJsonSuccess(integer);
    }

    //生成二维码
    @RequestMapping("getCode")
    public JsonData getCode(String orderId) throws Exception {
        Map map = consService.selectgetCode(orderId);
        return JsonData.getJsonSuccess(map);
    }


    //修改总数
    @RequestMapping("updateCount")
    public JsonData updateCount(Integer shopId , Integer count){
        consService.updateCount(shopId,count);
        return JsonData.getJsonSuccess("修改成功");
    }

    //提交订单
    @RequestMapping("addFrom")
    public JsonData addFrom(Integer shopId ,Integer val,String flag) throws CountException {
        boolean exists = RedisUse.exists(flag);
        if (exists==true){
            return JsonData.getJsonError(3,"正在处理中");
        }else {
            RedisUse.set(flag,"",4);
        }
        Map map =  consService.addFrom(shopId,val);
        return JsonData.getJsonSuccess(map);
    }


    //更换收件人
    @RequestMapping("updateParent")
    public JsonData updateParent(Integer m){
        consService.updateParent(m);
        return JsonData.getJsonSuccess("修改成功");
    }

    //订单信息展示
    @RequestMapping("selectCartShow")
    public JsonData selectCartShow(){
       List<Cart>  cartList= consService.selectCartShow();
        return JsonData.getJsonSuccess(cartList);
    }

    //被选中展示
    @RequestMapping("selectParent")
    public JsonData selectParent(Integer nu){
        List<Cons>cons = consService.selectParent(nu);
        return JsonData.getJsonSuccess(cons);
    }

    //新增收件人地区展示
    @RequestMapping("selectAreaShow")
    public JsonData selectAreaShow(){
        String s = consService.selectAreaShow();
        return JsonData.getJsonSuccess(s);
    }

    //注册收货人信息
    @RequestMapping("addShouJianParent")
    public JsonData addShouJianParent(Cons cons){
        consService.addShouJianParent(cons);
        return JsonData.getJsonSuccess("注册成功");
    }



}
