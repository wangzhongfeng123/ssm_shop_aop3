package com.fh.controller;

import com.fh.dao.VipDao;
import com.fh.model.po.Vip;
import com.fh.service.VipService;
import com.fh.util.JsonData;
import com.fh.util.OSSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("vip")
public class VipController {

    @Autowired
    private VipDao vipDao;

    @Autowired
    private VipService vipService;


    //登录
    @RequestMapping("login")
    public JsonData login(String iphone,String code){
        Map map = vipService.selectlogin(iphone,code);
        return JsonData.getJsonSuccess(map);
    }


    //生成验证码
    @RequestMapping("shoplogin")
    public JsonData shoplogin(String iphone){
        vipService.shoplogin(iphone);
        return JsonData.getJsonSuccess("生成验证码成功");
    }


    //注册用户名重复
    @RequestMapping("selectByVipName")
    public Map selectByVipName(String vipName){
        Map map = new HashMap();
        map.put("valid",vipService.selectByVipName(vipName));
        return map;
    }

    //注册
    @RequestMapping("addVip")
    public JsonData addVip(Vip vip){
        vip.setCreaDate(new Date());
        vipDao.insert(vip);
        return JsonData.getJsonSuccess("注册成功");
    }

    //图片上传
    @RequestMapping("uploadFile")
    public Map uploadFile(MultipartFile image) throws IOException {
        Map map = new HashMap();
        String imgPath = OSSUtils.assUtils(image);
        map.put("imgPath",imgPath);
        return map;
    }



}
