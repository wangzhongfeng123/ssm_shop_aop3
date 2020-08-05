package com.fh.service;

import com.fh.intercepter.exception.CountException;
import com.fh.model.po.Cart;
import com.fh.model.po.Cons;

import java.util.List;
import java.util.Map;

public interface ConsService {
    String selectAreaShow();

    void addShouJianParent(Cons cons);

    List<Cons> selectParent(Integer nu);

    List<Cart> selectCartShow();

    void updateParent(Integer m);

    Map addFrom(Integer shopId, Integer val) throws CountException;

    void updateCount(Integer shopId, Integer count);

    Integer selectCode(String orderId) throws Exception;

    Map selectgetCode(String orderId) throws Exception;
}
