package com.zulily.store.controller.controller;

import com.zulily.store.controller.Manager.SalesOrderManager;
import com.zulily.store.controller.model.SalesOrder;
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
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/orders", produces=MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
public class OrderController {

    private final static Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private SalesOrderManager ordersManager;

    @RequestMapping(value = "", method= RequestMethod.GET, consumes=MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
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


//    /**
//     * Sales Order API: Inserts and gets a sales record. A sales record includes the following attributes:
//     * order_id [int], product [string], count [int], created_at [timestamp], updated_at [timestamp]
//     * @param orderDetails
//     * @return
//     */
//    @RequestMapping(value = "", method= RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
//    public ResponseEntity<Integer> addOrder(@RequestBody(required = true) @Valid final SalesOrder orderDetails) {
//
//        LOGGER.info("POST /orders");
//
//        try {
//            //TODO: validate orderDetails
//
//            ordersManager.insertOrder(orderDetails);
//
//            LOGGER.debug("Order has been successfully inserted with _id = " + orderDetails.getOrderId());
//
//            return new ResponseEntity<Integer>(Integer.valueOf(orderDetails.getOrderId()), HttpStatus.CREATED);
//        }
//        catch (Exception e) {
//            LOGGER.error("Error inserted order with recordId {}", orderDetails.getOrderId());
//            return new ResponseEntity<Integer>(Integer.valueOf(orderDetails.getOrderId()), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }


    /**
     * Sales Order API: Inserts list of sale records and gets record ids.
     * A sales record includes the following attributes:
     * order_id [int], product [string], count [int], created_at [timestamp], updated_at [timestamp]
     * @param orders
     * @return
     */
    @RequestMapping(value = "", method= RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    public ResponseEntity<List<Integer>> addOrder(@RequestBody(required = true) @Valid final List<SalesOrder> orders) {

        LOGGER.info("POST /orders");

        try {
            //TODO: validate orderDetails

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