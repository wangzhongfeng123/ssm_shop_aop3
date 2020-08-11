package com.fh.controller;

import com.fh.dao.OrderdetailDao;
import com.fh.model.po.Cart;
import com.fh.model.vo.CartShow;
import com.fh.service.CartService;
import com.fh.util.JsonData;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("car")
@Api(value = "购物车",description = "购物车API")
public class CartController {


    @Autowired
    private CartService cartService;

    @Autowired
    private OrderdetailDao orderdetailDao;




    @RequestMapping("updateCart1")
    public JsonData updateCart(Integer id){
        cartService.updateCartByType(id);
        return JsonData.getJsonSuccess("修改成功");
    }

    @RequestMapping("deleteCart")
    public JsonData deleteCart(Integer id){
        orderdetailDao.deleteById(id);
        return JsonData.getJsonSuccess("删除成功");
    }

    //订单展示
    @RequestMapping("selectCartShow")
    public JsonData selectCartShow(){
        List<CartShow> cartList = cartService.selectCartShow();
        return JsonData.getJsonSuccess(cartList);
    }


    //修改复选框选中状态
    @RequestMapping("updateChecked")
    public JsonData updateChecked(String str,Integer shopId){
        cartService.updateChecked(str,shopId);
        return JsonData.getJsonSuccess("修改成功");
    }

    //删除
    @RequestMapping("deleteShop")
    public JsonData deleteShop(Integer shopId){
        cartService.deleteShop(shopId);
        return JsonData.getJsonSuccess("删除成功");
    }

    //批量删除购物车商品
    @RequestMapping("deleteAll")
    public JsonData deleteAll(@RequestParam("str[]") List<Integer> str){
        cartService.deleteAll(str);
        return JsonData.getJsonSuccess("删除成功");
    }

    //加减数量
    @RequestMapping("updateCart")
    public JsonData updateCart(Integer shopId,Integer nu,Integer count){
        cartService.updateCart(shopId,nu,count);
        return JsonData.getJsonSuccess("成功");
    }

    //购物车展示
    @RequestMapping("selectCart")
    public JsonData selectCart(){
        List<String> cartList = cartService.selectCart();
        return JsonData.getJsonSuccess(cartList);
    }

    //加入购物车
    @ApiOperation(value = "加入购物车",notes = "根据Cart对象加入购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "Cart" ,name = "cart",value = "购物车信息",required = true)
    })
    @ApiResponses({
            @ApiResponse(code = 1000,message = "接口异常")
    })
    @RequestMapping("addCar")
    public JsonData addCar(Cart cart){
       long hlen =  cartService.addCar(cart);
       return JsonData.getJsonSuccess(hlen);
    }

}
