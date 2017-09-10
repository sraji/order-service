package com.zulily.store.controller.Manager;

import com.zulily.store.controller.model.ProductType;
import com.zulily.store.controller.model.SalesOrder;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Collection of orders placed
 */
@Component
public class SalesOrderManager {
    TreeMap<CustomSalesOrderKey, SalesOrder> salesOrders;

    public SalesOrderManager() {

        Comparator<CustomSalesOrderKey> sortByUpdatedDate = new Comparator<CustomSalesOrderKey>(){
            public int compare(CustomSalesOrderKey o1, CustomSalesOrderKey o2) {
                if ( (o1.getUpdatedAt().compareTo(o2.getUpdatedAt())) > 0) {
                    return 1;
                } else{
                    return -1;
                }
            }
        };

        salesOrders = new TreeMap<CustomSalesOrderKey, SalesOrder>(sortByUpdatedDate);
    }

    /**
     * Returns top selling products in provided timeframe
     * @param begin
     * @param end
     * @param count
     * @return
     */
    public Set<ProductType> getTopSellingProducts(DateTime begin, DateTime end, Integer count) {
        // Get all orders with updatedDateTime between begin and end
        Collection<SalesOrder> orders = getSalesOrders(begin, end);

        // Construct a map: ProductType to total quantity
        Map<ProductType, Integer> totalOrders = orders.stream().collect(Collectors.groupingBy(SalesOrder::getProduct,
                Collectors.summingInt(SalesOrder::getQuantity)));

        // Sort the map based on values
        Set<ProductType> types = totalOrders.entrySet().stream()
                .sorted(Map.Entry.<ProductType, Integer>comparingByValue().reversed())
                .limit(count)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

        return types;
    }

    /**
     * Returns all orders placed so far
     * @return
     */
    public Collection<SalesOrder> getOrders() {
        return salesOrders.values();
    }

    /**
     * Returns an order
     * @param orderId
     * @return
     */
    public SalesOrder getOrder(Integer orderId) {
        for (Map.Entry<CustomSalesOrderKey, SalesOrder> entry: salesOrders.entrySet()) {
            if (entry.getKey().getOrderID().equals(orderId)) {
                return entry.getValue();
            }
        }

        return null;
    }

    /**
     * Inserts provided order in the map.
     * If a record already exists (same order id), previous record is deleted and new record is updated
     * @param orderDetails
     */
    public void insertOrder(SalesOrder orderDetails) {
        deleteRecord(orderDetails.getOrderId());

        CustomSalesOrderKey key = new CustomSalesOrderKey(orderDetails.getOrderId(), orderDetails.getUpdatedAt());
        salesOrders.put(key, orderDetails);
    }

    /**
     * Inserts provided order in the map.
     * If a record already exists (same order id), previous record is deleted and new record is updated
     * @param orderDetails
     */
    public void insertOrders(List<SalesOrder> orderDetails) {
        if (CollectionUtils.isEmpty(orderDetails)) {
            return;
        }

        //TODO: Handle failed records

        Iterator<SalesOrder> iter = orderDetails.iterator();
        while (iter.hasNext()) {
            insertOrder(iter.next());
        }
    }

    /**
     * Deletes a record if exists
     * @param orderId
     */
    private void deleteRecord(Integer orderId) {
        Iterator<Map.Entry<CustomSalesOrderKey, SalesOrder>> iter = salesOrders.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<CustomSalesOrderKey, SalesOrder> entry = iter.next();
            if(entry.getKey().getOrderID().equals(orderId)){
                iter.remove();
            }
        }
    }

    private Collection<SalesOrder> getSalesOrders(DateTime begin, DateTime end) {
        CustomSalesOrderKey beginKey = new CustomSalesOrderKey(begin);
        CustomSalesOrderKey endKey = new CustomSalesOrderKey(end);
        return salesOrders.subMap(beginKey, true, endKey, true).values();
    }
}

