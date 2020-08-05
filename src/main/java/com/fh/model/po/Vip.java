package com.fh.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@TableName("t_vip")
public class Vip {

    @TableId(value = "vipId", type = IdType.AUTO)
    private Integer vipId;
    @TableField("vipName")
    private String vipName;
    @TableField("vipPhone")
    private String vipPhone;
    @TableField("vipPath")
    private String vipPath;
    @TableField("area")
    private String area;
    @TableField("creaDate")
    @DateTimeFormat( pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date creaDate;


    public Integer getVipId() {
        return vipId;
    }

    public void setVipId(Integer vipId) {
        this.vipId = vipId;
    }

    public String getVipName() {
        return vipName;
    }

    public void setVipName(String vipName) {
        this.vipName = vipName;
    }

    public String getVipPhone() {
        return vipPhone;
    }

    public void setVipPhone(String vipPhone) {
        this.vipPhone = vipPhone;
    }

    public String getVipPath() {
        return vipPath;
    }

    public void setVipPath(String vipPath) {
        this.vipPath = vipPath;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Date getCreaDate() {
        return creaDate;
    }

    public void setCreaDate(Date creaDate) {
        this.creaDate = creaDate;
    }
}
