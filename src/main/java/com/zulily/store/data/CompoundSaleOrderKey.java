package com.zulily.store.data;


import com.zulily.store.model.SalesOrder;
import org.joda.time.DateTime;

public class CompoundSaleOrderKey implements Comparable{
    private Integer orderID;
    private DateTime updateDateTime;

    public CompoundSaleOrderKey(SalesOrder order) {
        this.orderID = order.getOrderId();
        this.updateDateTime = order.getUpdatedAt();
    }

    public CompoundSaleOrderKey (DateTime dt) {
        this.updateDateTime = dt;
    }

    public DateTime getUpdateDateTime() {
        return updateDateTime;
    }

    public void setUpdateDateTime(DateTime updateDateTime) {
        this.updateDateTime = updateDateTime;
    }

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    @Override
    public int compareTo(Object o) {

        CompoundSaleOrderKey ck = (CompoundSaleOrderKey)o;

        if (this.getUpdateDateTime().compareTo(ck.getUpdateDateTime()) > 0) {
            return 1;
        } else{
            return -1;
        }
    }
}
