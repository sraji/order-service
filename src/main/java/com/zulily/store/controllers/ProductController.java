package com.zulily.store.controllers;

import com.zulily.store.controllers.exceptions.InvalidInputException;
import com.zulily.store.controllers.responses.Error;
import com.zulily.store.controllers.responses.TopSellingProductsResponse;
import com.zulily.store.services.SalesOrderService;
import com.zulily.store.model.ProductType;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping(value="/orders/products", produces= MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
public class ProductController {
    private final static Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    private static final int INVALID_DATES_ERROR_CODE = 4000;
    private static final int INVALID_COUNT_ERROR_CODE = 5000;
    private static final int UNKNOWN_ERROR_CODE = 6000;

    @Autowired
    private SalesOrderService salesOrderService;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> exceptionHandler(Exception ex) {
        Error e = new Error();
        e.setDescription(ex.getMessage());
        e.setErrorCode(UNKNOWN_ERROR_CODE);
        return new ResponseEntity<Error>(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidInputException.class)
    public ResponseEntity<Error> invalidInputExceptionHandler(InvalidInputException ex) {
        Error e = new Error();
        e.setDescription(ex.getMessage());
        e.setErrorCode(ex.getErrorCode());
        return new ResponseEntity<Error>(e, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "", method= RequestMethod.GET, consumes=MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    public ResponseEntity<TopSellingProductsResponse> getTopSellingOrders(@RequestParam(value="begin", required=true) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") DateTime begin,
                                                                          @RequestParam(value="end", required=true)   @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") DateTime end,
                                                                          @RequestParam(value="count", required=true) final Integer count)
    throws Exception{
        TopSellingProductsResponse response = new TopSellingProductsResponse();

        try {
            if (begin.compareTo(end) > 0) {
                throw new InvalidInputException("begin date cannot be greater than end", INVALID_DATES_ERROR_CODE);
            }

            if (count < 0) {
                throw new InvalidInputException("count cannot be negative", INVALID_COUNT_ERROR_CODE);
            }

            Set<ProductType> products = salesOrderService.getTopSellingProducts(begin, end, count);
            response.setProducts(products);
            return new ResponseEntity<TopSellingProductsResponse>(response, HttpStatus.OK);
        }
        catch (Exception e) {
            LOGGER.error("Error retrieving records");
            throw e;
        }
    }

}

