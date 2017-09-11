package com.zulily.store.data;

import com.zulily.store.model.SalesOrder;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

@Component
public class SaleOrdersCollection {

    private TreeMap<CompoundSaleOrderKey, SalesOrder> orderedSales;
    private HashMap<Integer, CompoundSaleOrderKey> orderIdToCompoundKeyMap;

    public SaleOrdersCollection() {
        orderedSales = new TreeMap();
        orderIdToCompoundKeyMap = new HashMap<>();
    }

    public Collection<SalesOrder> getRecords() {
        return this.orderedSales.values();
    }

    public SalesOrder getRecord(Integer orderId) {
        if (orderId == null) {
            return null;
        }
        // Get compoundKey from orderIdToCompoundKeyMap
        CompoundSaleOrderKey key = orderIdToCompoundKeyMap.get(orderId);

        if (key != null) {
            return orderedSales.get(key);
        }

        return null;
    }

    public void addRecord(SalesOrder orderDetails) {
        if (orderDetails == null) {
            return;
        }

        CompoundSaleOrderKey compoundKey = new CompoundSaleOrderKey(orderDetails);

        this.orderIdToCompoundKeyMap.put(orderDetails.getOrderId(), compoundKey);

        //TODO: Duplicate record
//        if (orderedSales.containsKey(compoundKey)) {
//            orderedSales.remove(compoundKey);
//        }

        this.orderedSales.put(compoundKey, orderDetails);
    }

    public void addRecords(List<SalesOrder> orderDetails) {
        if (CollectionUtils.isEmpty(orderDetails)) {
            return;
        }

        Iterator<SalesOrder> iter = orderDetails.iterator();

        while (iter.hasNext()) {
            addRecord(iter.next());
        }
    }

    public Collection<SalesOrder> getSalesOrders(DateTime begin, DateTime end) {

        CompoundSaleOrderKey beginDt = new CompoundSaleOrderKey(begin);
        CompoundSaleOrderKey endDt = new CompoundSaleOrderKey(end);

        Collection<SalesOrder> orders = orderedSales.subMap(beginDt, true, endDt,true).values();

        return orders;
    }
}
