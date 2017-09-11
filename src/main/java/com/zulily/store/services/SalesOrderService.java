package com.zulily.store.services;

import com.zulily.store.data.SaleOrdersCollection;
import com.zulily.store.model.ProductType;
import com.zulily.store.model.SalesOrder;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service class integrating with data to retrieve sale records
 * Collection of orders placed
 */
@Service
public class SalesOrderService {

    @Autowired
    private SaleOrdersCollection saleOrdersPlaced;

    /**
     * Returns top selling products in provided timeframe
     * @param begin
     * @param end
     * @param count
     * @return
     */
    public Set<ProductType> getTopSellingProducts(DateTime begin, DateTime end, Integer count) throws IllegalArgumentException{

        if (begin == null || end == null || begin.compareTo(end) > 0 || count < 0) {
            throw new IllegalArgumentException();
        }

        // Get all orders with updatedDateTime between begin and end
        Set<SalesOrder> orders = saleOrdersPlaced.getSalesOrders(begin, end);

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
        return saleOrdersPlaced.getRecords();
    }

    /**
     * Returns an order
     * @param orderId
     * @return
     */
    public SalesOrder getOrder(Integer orderId) {
        if (orderId == null) {
            return null;
        }
        return saleOrdersPlaced.getRecord(orderId);
    }

    /**
     * Inserts provided order in the map.
     * If a record already exists (same order id), previous record is deleted and new record is updated
     * @param orderDetails
     */
    public void insertOrder(SalesOrder orderDetails) {
        saleOrdersPlaced.addRecord(orderDetails);
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

        saleOrdersPlaced.addRecords(orderDetails);
    }
}

