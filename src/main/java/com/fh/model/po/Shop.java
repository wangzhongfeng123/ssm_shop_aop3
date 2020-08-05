package com.fh.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@TableName("t_shop")
public class Shop {


    @TableId(value ="shopId" ,type = IdType.AUTO)
    private Integer shopId;
    @TableField("shopName")
    private String shopName;
    @TableField("price")
    private Double price;
    @TableField("shopIsUp")
    private Integer shopIsUp;
    @TableField("photograph")
    private String photograph;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @TableField("shopDate")
    private Date shopDate;

    @TableField("shopCakes")
    private Integer shopCakes;

    @TableField("area")
    private String area;
    @TableField(exist =false )
    private String areas;
    @TableField("typeId")
    private Integer type;

    @TableField("storyCount")
    private Integer storyCount;


    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getShopIsUp() {
        return shopIsUp;
    }

    public void setShopIsUp(Integer shopIsUp) {
        this.shopIsUp = shopIsUp;
    }

    public String getPhotograph() {
        return photograph;
    }

    public void setPhotograph(String photograph) {
        this.photograph = photograph;
    }

    public Date getShopDate() {
        return shopDate;
    }

    public void setShopDate(Date shopDate) {
        this.shopDate = shopDate;
    }

    public Integer getShopCakes() {
        return shopCakes;
    }

    public void setShopCakes(Integer shopCakes) {
        this.shopCakes = shopCakes;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAreas() {
        return areas;
    }

    public void setAreas(String areas) {
        this.areas = areas;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStoryCount() {
        return storyCount;
    }

    public void setStoryCount(Integer storyCount) {
        this.storyCount = storyCount;
    }
}
