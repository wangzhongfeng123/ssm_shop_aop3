package com.fh.service;

import com.fh.model.po.Cart;
import com.fh.model.vo.CartShow;

import java.util.List;

public interface CartService {
    long addCar(Cart cart);

    List<String> selectCart();

    void updateCart(Integer shopId, Integer nu, Integer count);

    void deleteAll(List<Integer> str);

    void deleteShop(Integer shopId);

    void updateChecked(String str ,Integer shopId);

    List<CartShow> selectCartShow();

    void updateCartByType(Integer id);
}
