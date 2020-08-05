package com.fh.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class ShopVo {


    private Integer shopId;
    private String shopName;
    private Double price;
    private Integer shopIsUp;
    private String photograph;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date shopDate;

    private Integer shopCakes;

    private String area;
    private String areas;
    private Integer type;

    private Integer storyCount;

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
