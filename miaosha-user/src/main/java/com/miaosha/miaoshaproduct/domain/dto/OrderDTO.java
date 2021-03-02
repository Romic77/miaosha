package com.miaosha.miaoshaproduct.domain.dto;

import java.math.BigDecimal;
import java.util.Date;

public class OrderDTO {
    private Long orderId;

    private Long productId;

    private String prodName;

    private String userId;

    private BigDecimal total;

    private String remarks;

    private Integer status;

    private Integer productNums;

    private Date createTime;

    private Date updateTime;

    private Date payTime;

    private Date dvyTime;

    private Date finallyTime;

    private Date cancelTime;

    private Boolean isPayed;

    private Integer deleteStatus;

    private Integer refundSts;

    private Byte orderType;

    private Byte closeType;

    public OrderDTO(Long orderId, Long productId, String prodName, String userId, BigDecimal total, String remarks, Integer status, Integer productNums, Date createTime, Date updateTime, Date payTime, Date dvyTime, Date finallyTime, Date cancelTime, Boolean isPayed, Integer deleteStatus, Integer refundSts, Byte orderType, Byte closeType) {
        this.orderId = orderId;
        this.productId = productId;
        this.prodName = prodName;
        this.userId = userId;
        this.total = total;
        this.remarks = remarks;
        this.status = status;
        this.productNums = productNums;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.payTime = payTime;
        this.dvyTime = dvyTime;
        this.finallyTime = finallyTime;
        this.cancelTime = cancelTime;
        this.isPayed = isPayed;
        this.deleteStatus = deleteStatus;
        this.refundSts = refundSts;
        this.orderType = orderType;
        this.closeType = closeType;
    }

    public OrderDTO() {
        super();
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName == null ? null : prodName.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getProductNums() {
        return productNums;
    }

    public void setProductNums(Integer productNums) {
        this.productNums = productNums;
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

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Date getDvyTime() {
        return dvyTime;
    }

    public void setDvyTime(Date dvyTime) {
        this.dvyTime = dvyTime;
    }

    public Date getFinallyTime() {
        return finallyTime;
    }

    public void setFinallyTime(Date finallyTime) {
        this.finallyTime = finallyTime;
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    public Boolean getIsPayed() {
        return isPayed;
    }

    public void setIsPayed(Boolean isPayed) {
        this.isPayed = isPayed;
    }

    public Integer getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Integer deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public Integer getRefundSts() {
        return refundSts;
    }

    public void setRefundSts(Integer refundSts) {
        this.refundSts = refundSts;
    }

    public Byte getOrderType() {
        return orderType;
    }

    public void setOrderType(Byte orderType) {
        this.orderType = orderType;
    }

    public Byte getCloseType() {
        return closeType;
    }

    public void setCloseType(Byte closeType) {
        this.closeType = closeType;
    }
}