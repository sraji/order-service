package com.zulily.store.controllers;

import com.zulily.store.manager.SalesOrderManager;
import com.zulily.store.model.SalesOrder;
import com.zulily.store.validators.SalesOrderValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO: Finish validation in the orders controller
 */
@RestController
@RequestMapping(value="/orders", consumes=MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8", produces=MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
public class OrdersController {

    private final static Logger LOGGER = LoggerFactory.getLogger(OrdersController.class);

    @Autowired
    private SalesOrderValidator salesOrderValidator;

    @Autowired
    private SalesOrderManager ordersManager;

    @GetMapping("")
    public ResponseEntity<Collection<SalesOrder>> getOrders() {

        try {
            Collection<SalesOrder> result = ordersManager.getOrders();

            return new ResponseEntity<Collection<SalesOrder>>(result, HttpStatus.OK);
        }
        catch (Exception e) {
            LOGGER.error("Error retrieving orders");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalesOrder> getOrders(@PathVariable("id") final Integer orderId) {

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
     * Sales Order API: Inserts list of sale records and gets record ids.
     * A sales record includes the following attributes:
     * order_id [int], product [string], count [int], created_at [timestamp], updated_at [timestamp]
     * @param orders
     * @return
     */
    @PostMapping("")
    public ResponseEntity<List<Integer>> addOrders(@RequestBody(required = true) @Validated final List<SalesOrder> orders) {

        LOGGER.info("POST /orders");

        try {
            ordersManager.insertOrders(orders);

            LOGGER.debug("Orders has been successfully inserted");

            List<Integer> orderIds = orders.stream().map(SalesOrder::getOrderId).collect(Collectors.toList());
            return new ResponseEntity<List<Integer>>(orderIds, HttpStatus.CREATED);
        }
        catch (Exception e) {
            List<Integer> orderIds = orders.stream().map(SalesOrder::getOrderId).collect(Collectors.toList());
            LOGGER.error("Error inserted order with recordId {}", orderIds.toString());
            return new ResponseEntity<List<Integer>>(orderIds, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}