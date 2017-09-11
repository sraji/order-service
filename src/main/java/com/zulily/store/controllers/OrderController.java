package com.zulily.store.controllers;

import com.zulily.store.controllers.responses.AddOrderResponse;
import com.zulily.store.services.SalesOrderService;
import com.zulily.store.model.SalesOrder;
import com.zulily.store.validators.SalesOrderValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO: Finish validation in the orders controller
 */
@RestController
@RequestMapping(value="/order", produces=MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
public class OrderController {

    private final static Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private SalesOrderValidator salesOrderValidator;

    @Autowired
    private SalesOrderService salesOrderService;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(salesOrderValidator);
    }


    @RequestMapping(value = "/{id}", method= RequestMethod.GET, consumes=MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    public ResponseEntity<SalesOrder> getOrders(@PathVariable("id") final Integer orderId) {

        try {
            SalesOrder result = salesOrderService.getOrder(orderId);

            if (result == null) {
                return new ResponseEntity<SalesOrder>(result, HttpStatus.NOT_FOUND);
            }

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
    public ResponseEntity<AddOrderResponse> addOrder(@RequestBody(required = true) @Validated final SalesOrder orderDetails, BindingResult result) {

        LOGGER.info("POST /order");
        if (result.hasErrors()) {
            LOGGER.error("Error with input {}", result.getAllErrors().toString());
            AddOrderResponse response = new AddOrderResponse();
            response.setOrderId(orderDetails.getOrderId());

            response.setErrors(result.getAllErrors());
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }

        AddOrderResponse response = new AddOrderResponse();
        response.setOrderId(orderDetails.getOrderId());

        try {
            salesOrderService.insertOrder(orderDetails);

            LOGGER.debug("Order has been successfully inserted with id {} ", orderDetails.getOrderId());

            return new ResponseEntity<AddOrderResponse>(response, HttpStatus.CREATED);
        }
        catch (Exception e) {
            LOGGER.error("Error inserted order with recordId {}", orderDetails.getOrderId());
            return new ResponseEntity<AddOrderResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}