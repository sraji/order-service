package com.zulily.store.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.joda.time.DateTime;

import javax.validation.constraints.NotNull;

/*
Model representation of an order
order_id,product,quantity,created_at,updated_at
546809878,NewBalance Shoes,6,2009-12-10 08:28:04,2015-07-23 18:31:33
*/
@JsonIgnoreProperties(ignoreUnknown = true)
public class SalesOrder{

    @NotNull
    private Integer orderId;

    @NotNull
    private ProductType product;

    @NotNull
    private Integer quantity;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private DateTime createdAt;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private DateTime updatedAt;

    public SalesOrder() {

    }

    public SalesOrder(int orderId, ProductType product, int quantity, DateTime createdAt, DateTime updatedAt) {
        this.orderId = orderId;
        this.product = product;
        this.quantity = quantity;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public ProductType getProduct() {
        return product;
    }

    public void setProduct(ProductType product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(DateTime createdAt) {
        this.createdAt = createdAt;
    }

    public DateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(DateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object object) {
        if(object==null){
            return false;
        }
        return this.getOrderId() == ((SalesOrder)object).getOrderId();
    }
}
