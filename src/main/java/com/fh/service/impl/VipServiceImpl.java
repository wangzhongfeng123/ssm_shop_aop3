package com.fh.service.impl;

import com.fh.dao.VipDao;
import com.fh.model.po.Vip;
import com.fh.service.VipService;
import com.fh.util.JWT;
import com.fh.util.RedisUse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VipServiceImpl implements VipService {

    @Autowired
    private VipDao vipDao;

    //注册用户名重复
    @Override
    public boolean selectByVipName(String vipName) {
        Vip vip = vipDao.selectByVipName(vipName);
        if (vip==null){
            return true;
        }else  {
            return false;
        }
    }
    //生成验证码
    @Override
    public void shoplogin(String iphone) {
        if (StringUtils.isEmpty(iphone)!=true){
            List<Vip> vipList = vipDao.selectByIphone(iphone);
            for (int i = 0; i <vipList.size() ; i++) {
                if (vipList.get(i).getVipName().equals(iphone)){
                    String a = "1111";
                    RedisUse.set("code"+iphone,a);
                }else if (vipList.get(i).getVipPhone().equals(iphone)){
                    String a = "1111";
                    RedisUse.set("code"+iphone,a);
                }
            }
        }
    }

    //登录
    @Override
    public Map selectlogin(String iphone, String code) {
        String s = RedisUse.get("code" + iphone);
        Map map = new HashMap();
        if (s != null && s.equals(code)){
            List<Vip> vipList = vipDao.selectByIphone(iphone);
            if (StringUtils.isEmpty(vipList)!=true){
                Vip vip = new Vip();
                for (int i = 0; i <vipList.size() ; i++) {
                    if (vipList.get(i).getVipName().equals(iphone)){
                        String iphoneOrname =  vipList.get(i).getVipName();
                        vip.setVipName(iphoneOrname);
                    }else if (vipList.get(i).getVipPhone().equals(iphone)){
                        String iphoneOrname = vipList.get(i).getVipPhone();
                        vip.setVipName(iphoneOrname);
                    }
                }
                String sign = JWT.sign(vip, 1000 * 60 * 60 * 24);
                RedisUse.set("login_"+iphone,sign,60*30);
                String token = Base64.getEncoder().encodeToString((iphone+","+sign).getBytes());
                map.put("status",200);
                map.put("message","登录成功");
                map.put("token",token);
            }else {
                map.put("code",2);
                map.put("message","没有改用户");
            }
        }else {
            map.put("code",1);
            map.put("message","没有生成验证码，验证码错误");
        }
        return map;
    }
}
