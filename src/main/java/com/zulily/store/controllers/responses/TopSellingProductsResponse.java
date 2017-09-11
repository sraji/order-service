package com.zulily.store.controllers.responses;

import com.zulily.store.model.ProductType;

import java.util.List;
import java.util.Set;

/**
 * Class that represents the body of the response sent to /order
 */
public class TopSellingProductsResponse {
    Set<ProductType> products;
    List<Error> errors;

    public Set<ProductType> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductType> products) {
        this.products = products;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public void addError(Error error) {
        this.getErrors().add(error);
    }

    public void setErrors(List<Error> errors) {
        this.getErrors().addAll(errors);
    }
}
