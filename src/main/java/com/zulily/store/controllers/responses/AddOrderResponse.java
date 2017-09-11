package com.zulily.store.controllers.responses;

import org.springframework.validation.ObjectError;

import java.util.List;

/**
 * Class that represents the body of the response sent to /order
 */
public class AddOrderResponse {
    public Integer orderId;
    public List<Error> errors;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public List<Error> getErrors() {
        return errors;
    }


    public void setErrors(List<ObjectError> errors) {
        for (ObjectError error : errors) {
            Error e = new Error();
            e.setDescription(error.toString());
            this.errors.add(e);
        }
    }
}
