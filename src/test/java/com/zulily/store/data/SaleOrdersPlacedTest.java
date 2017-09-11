package com.zulily.store.data;

import com.zulily.store.factory.SaleOrderObjectFactory;
import com.zulily.store.model.SalesOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class SaleOrdersPlacedTest {

    private final SaleOrderObjectFactory saleOrderObjectFactory  = new SaleOrderObjectFactory();

    public SaleOrdersCollection saleOrdersCollection;

    @Before
    public void setup() {
        saleOrdersCollection = new SaleOrdersCollection();
    }

    @Test
    public void getRecords() throws Exception {

    }

    @Test
    public void getRecord() throws Exception {

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
    public void addRecords() throws Exception {

    }

    @Test
    public void getSalesOrders() throws Exception {

    }

}