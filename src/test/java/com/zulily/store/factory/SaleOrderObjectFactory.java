package com.zulily.store.factory;

import com.zulily.store.model.ProductType;
import com.zulily.store.model.SalesOrder;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SaleOrderObjectFactory {

    public static List<SalesOrder> createSaleOrderList(Integer... saleOrderIds) {
        List<SalesOrder> orders = new ArrayList<SalesOrder>();
        for (Integer orderId : saleOrderIds)
        {
            SalesOrder order = createOrder(orderId);
            orders.add(order);
        }
        return orders;
    }

    public static SalesOrder createOrder(Integer orderId) {
        SalesOrder order = new SalesOrder();
        order.setOrderId(orderId);
        order.setProduct(ProductType.NEWBALANCE_SHOES);
        Random rand = new Random();
        order.setQuantity(rand.nextInt(100));

        DateTime ct = new DateTime();
        ct.withYear(2000);
        ct.plusHours(2);

        order.setCreatedAt(ct);

        DateTime ut = ct.plusMonths(5);
        order.setUpdatedAt(ut);

        return order;
    }
}


