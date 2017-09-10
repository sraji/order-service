package com.zulily.store.manager;


import org.joda.time.DateTime;

public class CustomSalesOrderKey {
    private Integer orderID;
    private DateTime updatedAt;

    public CustomSalesOrderKey(Integer orderID, DateTime updatedAt) {
        this.orderID = orderID;
        this.updatedAt = updatedAt;
    }

    public CustomSalesOrderKey(DateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    public DateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(DateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
