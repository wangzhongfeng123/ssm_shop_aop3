package com.fh.controller;


import com.alibaba.fastjson.JSONObject;
import com.fh.model.po.Area;
import com.fh.service.TypeService;
import com.fh.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("type")
public class TypeController {

    @Autowired
    private TypeService typeService;


    //地区
    @RequestMapping("selectAreaShow")
    public JsonData selectAreaShow(){
        List<String> areas =  typeService.selectAreaShow();
        List<Area>stringList=new ArrayList<>();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i <areas.size() ; i++) {
            String s = areas.get(i);
            Area area = JSONObject.parseObject(s, Area.class);
            stringList.add(area);
        }
        String string = JSONObject.toJSONString(stringList);
        return JsonData.getJsonSuccess(string);
    }

    //类型
    @RequestMapping("selectType")
    public JsonData selectType(){
        String typeList = typeService.selectType();
        return JsonData.getJsonSuccess(typeList);
    }



}
