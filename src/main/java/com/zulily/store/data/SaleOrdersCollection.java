package com.zulily.store.data;

import com.zulily.store.model.SalesOrder;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

@Component
public class SaleOrdersCollection {

    private TreeMap<DateTime, Set<SalesOrder>> orderedSales;
    private HashMap<Integer, SalesOrder> map;

    public SaleOrdersCollection() {
        orderedSales = new TreeMap();
        map = new HashMap<>();
    }

    public Collection<SalesOrder> getRecords() {
        return this.map.values();
    }

    public SalesOrder getRecord(Integer orderId) {
        return this.map.get(orderId);
    }

    public void addRecord(SalesOrder orderDetails) {
        this.map.put(orderDetails.getOrderId(), orderDetails);

        if (orderedSales.containsKey(orderDetails.getUpdatedAt())) {
            Set<SalesOrder> orders = orderedSales.get(orderDetails.getUpdatedAt());
            orders.add(orderDetails);
            this.orderedSales.put(orderDetails.getUpdatedAt(), orders);
        }
        else {
            Set<SalesOrder> orders = new HashSet<>();
            orders.add(orderDetails);
            this.orderedSales.put(orderDetails.getUpdatedAt(), orders);
        }

    }

    public void addRecords(List<SalesOrder> orderDetails) {
        Iterator<SalesOrder> iter = orderDetails.iterator();

        while (iter.hasNext()) {
            addRecord(iter.next());
        }
    }

    public Set<SalesOrder> getSalesOrders(DateTime begin, DateTime end) {
        Set<SalesOrder> result = new HashSet<>();
        Collection<Set<SalesOrder>> orders = orderedSales.subMap(begin, true, end,true).values();

        Iterator<Set<SalesOrder>> iter = orders.iterator();
        while (iter.hasNext()) {
            result.addAll(iter.next());
        }

        return result;
    }
}
