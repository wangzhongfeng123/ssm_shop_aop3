package com.fh.service;

import java.util.Map;

public interface VipService {
    boolean selectByVipName(String vipName);

    void shoplogin(String iphone);

    Map selectlogin(String iphone, String code);
}
