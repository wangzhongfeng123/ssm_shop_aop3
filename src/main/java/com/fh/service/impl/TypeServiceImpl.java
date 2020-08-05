package com.fh.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fh.dao.AreaDao;
import com.fh.dao.TypeDao;
import com.fh.model.po.Area;
import com.fh.model.po.Type;
import com.fh.service.TypeService;
import com.fh.util.RedisUse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeDao typeDao;

    @Autowired
    private AreaDao areaDao;

    @Override
    public String selectType() {



        long t_area = RedisUse.hlen("t_area1");
        if (t_area==0 ){
            List<Area> areas = areaDao.selectList(null);
            for (int i = 0; i <areas.size() ; i++) {
                Area area = areas.get(i);
                String string = JSONObject.toJSONString(area);
                RedisUse.hset("t_area1",area.getAreaId()+"",string);
            }
        }

        String t_type = RedisUse.get("t_type");
        if (StringUtils.isEmpty(t_type)==true){
            List<Type> typeList = typeDao.selectList(null);
            String string = JSONObject.toJSONString(typeList);
            RedisUse.set("t_type",string);
        }
        return t_type;
    }

    @Override
    public List<String> selectAreaShow() {
        List<String> t_area1 = RedisUse.hvals("t_area1");
        return t_area1;
    }
}
