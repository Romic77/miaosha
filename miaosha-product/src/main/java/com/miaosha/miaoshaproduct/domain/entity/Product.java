package com.miaosha.miaoshaproduct.domain.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Product {
    private Long productId;

    private String productName;

    private BigDecimal productPrice;

    private Integer totalStocks;

    private Date createTime;

    private Date updateTime;

    private String content;

    public Product(Long productId, String productName, BigDecimal productPrice, Integer totalStocks, Date createTime, Date updateTime) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.totalStocks = totalStocks;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Product(Long productId, String productName, BigDecimal productPrice, Integer totalStocks, Date createTime, Date updateTime, String content) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.totalStocks = totalStocks;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.content = content;
    }

    public Product() {
        super();
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getTotalStocks() {
        return totalStocks;
    }

    public void setTotalStocks(Integer totalStocks) {
        this.totalStocks = totalStocks;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", totalStocks=" + totalStocks +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", content='" + content + '\'' +
                '}';
    }
}