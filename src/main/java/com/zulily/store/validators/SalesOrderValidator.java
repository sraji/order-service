package com.zulily.store.validators;


import com.zulily.store.model.SalesOrder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class SalesOrderValidator implements Validator{

    public boolean supports(Class clazz) {
        return SalesOrder.class.isAssignableFrom(clazz);
    }

    public void validate(Object target, Errors errors)
    {
        try {
            ValidationUtils.rejectIfEmpty(errors, "orderId", "error.orderId", "Order ID is required.");
            ValidationUtils.rejectIfEmpty(errors, "product", "error.product", "Product is required.");
            ValidationUtils.rejectIfEmpty(errors, "quantity", "error.quantity", "Quantity is required.");
            ValidationUtils.rejectIfEmpty(errors, "createdAt", "error.createdAt", "Created datetime is required.");
            ValidationUtils.rejectIfEmpty(errors, "updatedAt", "error.updatedAt", "Updated datetime is required.");

            SalesOrder p = (SalesOrder) target;
            if (p.getQuantity() < 0) {
                errors.rejectValue("quantity", "negative value");
            }
        }
        catch (Exception e) {
            return;
        }
    }

}
