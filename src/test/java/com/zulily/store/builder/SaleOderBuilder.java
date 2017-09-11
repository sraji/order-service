package com.zulily.store.builder;

import com.zulily.store.model.ProductType;
import com.zulily.store.model.SalesOrder;
import org.joda.time.DateTime;

public class SaleOderBuilder {
    SalesOrder order = new SalesOrder();

    public SalesOrder getSaleOrder() {
        return this.order;
    }

    public SalesOrder withAllParams(Integer orderId, ProductType type, Integer quantity, DateTime createdAt, DateTime updatedAt) {
        this.order.setOrderId(orderId);
        this.order.setProduct(type);
        this.order.setQuantity(quantity);
        this.order.setCreatedAt(createdAt);
        this.order.setCreatedAt(updatedAt);
        return this.order;
    }
    public SalesOrder withOrderId(Integer orderId) {
        this.order.setOrderId(orderId);
        return this.order;
    }

    public SalesOrder withProductType(ProductType type) {
        this.order.setProduct(type);
        return this.order;
    }

    public SalesOrder withQuantity(Integer quantity) {
        this.order.setQuantity(quantity);
        return this.order;
    }

    public SalesOrder withCreatedAt(DateTime createdAt) {
        this.order.setCreatedAt(createdAt);
        return this.order;
    }

    public SalesOrder withUpdatedAt(DateTime updatedAt) {
        this.order.setCreatedAt(updatedAt);
        return this.order;
    }

}
