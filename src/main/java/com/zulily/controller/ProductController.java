package com.zulily.controller;

import com.zulily.Manager.SalesOrderManager;
import com.zulily.model.ProductType;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping(value="/orders/products", produces= MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
public class ProductController {
    private final static Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private SalesOrderManager ordersManager;


    @RequestMapping(value = "", method= RequestMethod.GET, consumes=MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    public ResponseEntity<Set<ProductType>> getTopSellingOrders(@RequestParam(value="begin", required=true) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") DateTime begin,
                                                                      @RequestParam(value="end", required=true)   @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") DateTime end,
                                                                      @RequestParam(value="count", required=true) final Integer count) {

        try {
            //TODO: Validate input
            Set<ProductType> products = ordersManager.getTopSellingProducts(begin, end, count);
            return new ResponseEntity<Set<ProductType>>(products, HttpStatus.OK);
        }
        catch (Exception e) {
            LOGGER.error("Error inserted order with recordId {}");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

