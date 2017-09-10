package com.zulily.store.controllers;

import com.zulily.store.manager.SalesOrderManager;
import com.zulily.store.model.SalesOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * TODO: Finish validation in the orders controller
 */
@RestController
@RequestMapping(value="/order", produces=MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
public class OrderController {

    private final static Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private SalesOrderManager ordersManager;

    @RequestMapping(value = "/{id}", method= RequestMethod.GET, consumes=MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    public ResponseEntity<SalesOrder> getOrders(@PathVariable("id") @Valid final Integer orderId) {

        try {
            SalesOrder result = ordersManager.getOrder(orderId);

            return new ResponseEntity<SalesOrder>(result, HttpStatus.OK);
        }
        catch (Exception e) {
            LOGGER.error("Error retrieving orderId {}", orderId);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Sales Order API: Inserts and gets a sales record. A sales record includes the following attributes:
     * order_id [int], product [string], count [int], created_at [timestamp], updated_at [timestamp]
     * @param orderDetails
     * @return
     */
    @RequestMapping(value = "", method= RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    public ResponseEntity<Integer> addOrder(@RequestBody(required = true) @Valid final SalesOrder orderDetails) {

        LOGGER.info("POST /order");

        try {
            //TODO: validate orderDetails

            ordersManager.insertOrder(orderDetails);

            LOGGER.debug("Order has been successfully inserted with _id = " + orderDetails.getOrderId());

            return new ResponseEntity<Integer>(Integer.valueOf(orderDetails.getOrderId()), HttpStatus.CREATED);
        }
        catch (Exception e) {
            LOGGER.error("Error inserted order with recordId {}", orderDetails.getOrderId());
            return new ResponseEntity<Integer>(Integer.valueOf(orderDetails.getOrderId()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}