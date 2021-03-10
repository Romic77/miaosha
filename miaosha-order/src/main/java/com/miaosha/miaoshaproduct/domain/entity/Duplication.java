package com.miaosha.miaoshaproduct.domain.entity;

import java.util.Date;

public class Duplication {
    private Long duplicationId;

    private Date createTime;

    private String serviceName;

    private Long serviceId;

    public Duplication(Long duplicationId, Date createTime, String serviceName, Long serviceId) {
        this.duplicationId = duplicationId;
        this.createTime = createTime;
        this.serviceName = serviceName;
        this.serviceId = serviceId;
    }

    public Duplication() {
        super();
    }

    public Long getDuplicationId() {
        return duplicationId;
    }

    public void setDuplicationId(Long duplicationId) {
        this.duplicationId = duplicationId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName == null ? null : serviceName.trim();
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }
}