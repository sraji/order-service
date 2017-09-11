package com.zulily.store.data;

import com.zulily.store.factory.SaleOrderObjectFactory;
import com.zulily.store.model.SalesOrder;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import static org.hamcrest.Matchers.equalTo;

@RunWith(MockitoJUnitRunner.class)
public class SaleOrdersCollectionTest {

    private final SaleOrderObjectFactory saleOrderObjectFactory  = new SaleOrderObjectFactory();

    public SaleOrdersCollection saleOrdersCollection;

    @Before
    public void setup() {
        saleOrdersCollection = new SaleOrdersCollection();
    }

    @Test
    public void getRecordsEmpty() throws Exception {
        //arrange

        //act

        //assert
        assertEquals(0, saleOrdersCollection.getRecords().size());
    }

    @Test
    public void getRecords() throws Exception {
        //arrange
        SalesOrder orderPlaced123456 = saleOrderObjectFactory.createOrder(123456);
        SalesOrder orderPlaced45678 = saleOrderObjectFactory.createOrder(45678);

        //act
        saleOrdersCollection.addRecord(orderPlaced123456);
        saleOrdersCollection.addRecord(orderPlaced45678);

        //assert
        assertEquals(2, saleOrdersCollection.getRecords().size());
        assertTrue(saleOrdersCollection.getRecords().contains(orderPlaced45678));
        assertTrue(saleOrdersCollection.getRecords().contains(orderPlaced123456));
    }

    @Test
    public void getRecord() throws Exception {
        //arrange
        SalesOrder orderPlaced123456 = saleOrderObjectFactory.createOrder(123456);
        SalesOrder orderPlaced45678 = saleOrderObjectFactory.createOrder(45678);

        //act
        saleOrdersCollection.addRecord(orderPlaced123456);
        saleOrdersCollection.addRecord(orderPlaced45678);

        //assert
        assertEquals(2, saleOrdersCollection.getRecords().size());
        assertThat(saleOrdersCollection.getRecord(123456).getOrderId(),  equalTo(123456));
    }

    @Test
    public void getRecordNotFound() throws Exception {
        //arrange
        SalesOrder orderPlaced123456 = saleOrderObjectFactory.createOrder(123456);
        SalesOrder orderPlaced45678 = saleOrderObjectFactory.createOrder(45678);

        //act
        saleOrdersCollection.addRecord(orderPlaced123456);
        saleOrdersCollection.addRecord(orderPlaced45678);

        //assert
        assertEquals(2, saleOrdersCollection.getRecords().size());
        assertNull(saleOrdersCollection.getRecord(7890));
    }

    @Test
    public void addRecord() throws Exception {
        //arrange
        SalesOrder orderPlaced = saleOrderObjectFactory.createOrder(123456);

        //act
        saleOrdersCollection.addRecord(orderPlaced);

        //assert
        assertEquals(orderPlaced, saleOrdersCollection.getRecord(123456));
        assertEquals(1, saleOrdersCollection.getRecords().size());
    }

    @Test
    public void addRecordsSafetyCheck() throws Exception {
        //arrange
        List<SalesOrder> ordersNull = null;
        List<SalesOrder> ordersEmpty = Collections.EMPTY_LIST;

        //act
        saleOrdersCollection.addRecords(ordersNull);
        saleOrdersCollection.addRecords(ordersEmpty);

        //assert
        assertEquals(0, saleOrdersCollection.getRecords().size());
    }

    @Test
    public void addRecords() throws Exception {
        //arrange
        SalesOrder orderPlaced123456 = saleOrderObjectFactory.createOrder(123456);
        SalesOrder orderPlaced45678 = saleOrderObjectFactory.createOrder(45678);

        List<SalesOrder> orders = Arrays.asList(orderPlaced45678, orderPlaced123456);

        //act
        saleOrdersCollection.addRecords(orders);

        //assert
        assertEquals(2, saleOrdersCollection.getRecords().size());
    }

    @Test
    public void getSalesOrdersTimeRangeOne() throws Exception {
        // arrange
        DateTime begin = new DateTime();
        begin = begin.withYear(2000);
        begin = begin.withMonthOfYear(2);

        DateTime end = new DateTime();
        end = end.withYear(2001);
        end = end.withMonthOfYear(2);

        SalesOrder orderPlaced123456 = saleOrderObjectFactory.createOrder(123456);
        DateTime orderPlaced123456DT = new DateTime();
        orderPlaced123456DT = orderPlaced123456DT.withYear(2000);
        orderPlaced123456DT = orderPlaced123456DT.withMonthOfYear(10);
        orderPlaced123456.setUpdatedAt(orderPlaced123456DT);

        SalesOrder orderPlaced45678 = saleOrderObjectFactory.createOrder(45678);
        DateTime orderPlaced45678DT = new DateTime();
        orderPlaced45678DT = orderPlaced45678DT.withYear(2001);
        orderPlaced45678DT = orderPlaced45678DT.withMonthOfYear(10);
        orderPlaced45678.setUpdatedAt(orderPlaced45678DT);

        List<SalesOrder> orders = Arrays.asList(orderPlaced45678, orderPlaced123456);
        saleOrdersCollection.addRecords(orders);

        //act
        Collection<SalesOrder> actual = saleOrdersCollection.getSalesOrders(begin, end);

        //assert
        assertEquals(1, actual.size());
        List<SalesOrder> valuesToMatch = new ArrayList<>(actual);
        assertEquals(123456, valuesToMatch.get(0).getOrderId());
    }

    @Test
    public void getSalesOrdersTimeRangeAll() throws Exception {
        // arrange
        DateTime begin = new DateTime();
        begin = begin.withYear(2000);
        begin = begin.withMonthOfYear(2);

        DateTime end = new DateTime();
        end = end.withYear(2006);
        end = end.withMonthOfYear(2);

        SalesOrder orderPlaced123456 = saleOrderObjectFactory.createOrder(123456);
        DateTime orderPlaced123456DT = new DateTime();
        orderPlaced123456DT = orderPlaced123456DT.withYear(2000);
        orderPlaced123456DT = orderPlaced123456DT.withMonthOfYear(10);
        orderPlaced123456.setUpdatedAt(orderPlaced123456DT);

        SalesOrder orderPlaced45678 = saleOrderObjectFactory.createOrder(45678);
        DateTime orderPlaced45678DT = new DateTime();
        orderPlaced45678DT = orderPlaced45678DT.withYear(2001);
        orderPlaced45678DT = orderPlaced45678DT.withMonthOfYear(10);
        orderPlaced45678.setUpdatedAt(orderPlaced45678DT);

        List<SalesOrder> orders = Arrays.asList(orderPlaced45678, orderPlaced123456);
        saleOrdersCollection.addRecords(orders);

        //act
        Collection<SalesOrder> actual = saleOrdersCollection.getSalesOrders(begin, end);

        //assert
        assertEquals(2, actual.size());
    }

    @Test
    public void getSalesOrdersTimeRangeNone() throws Exception {
        // arrange
        DateTime begin = new DateTime();
        begin = begin.withYear(2005);
        begin = begin.withMonthOfYear(2);

        DateTime end = new DateTime();
        end = end.withYear(2006);
        end = end.withMonthOfYear(2);

        SalesOrder orderPlaced123456 = saleOrderObjectFactory.createOrder(123456);
        DateTime orderPlaced123456DT = new DateTime();
        orderPlaced123456DT = orderPlaced123456DT.withYear(2000);
        orderPlaced123456DT = orderPlaced123456DT.withMonthOfYear(10);
        orderPlaced123456.setUpdatedAt(orderPlaced123456DT);

        SalesOrder orderPlaced45678 = saleOrderObjectFactory.createOrder(45678);
        DateTime orderPlaced45678DT = new DateTime();
        orderPlaced45678DT = orderPlaced45678DT.withYear(2001);
        orderPlaced45678DT = orderPlaced45678DT.withMonthOfYear(10);
        orderPlaced45678.setUpdatedAt(orderPlaced45678DT);

        List<SalesOrder> orders = Arrays.asList(orderPlaced45678, orderPlaced123456);
        saleOrdersCollection.addRecords(orders);

        //act
        Collection<SalesOrder> actual = saleOrdersCollection.getSalesOrders(begin, end);

        //assert
        assertEquals(0, actual.size());
    }

}