package com.zulily.store.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Enum for list of products
 */
public enum ProductType {
    NEWBALANCE_SHOES("NewBalance Shoes"),
    NIKE_SHOES("Nike Shoes"),
    REEBOK_SHORTS("Reebox Shorts"),
    NORTHFACE_JACKET("Northface Jacket"),
    NIKE_TSHIRTS("Nike T-shirts"),
    TOMMY_JERSEY("Tommy Jersey"),
    NEWBALANCE_TSHIRTS("NewBalance T-shirts"),
    NIKE_PANTS("Nike Pants"),
    NORTHFACE_WINTER_GLOVES("Northface Winter Gloves"),
    REEBOX_JERSEY("Reebox Jersey"),
    TOMMY_PERFUME("Tommy Perfume"),
    NIKE_GOLF_GLOVES("Nike Golf Gloves");


    private final String value;

    ProductType(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    @JsonCreator
    public static ProductType getEnumFromValue(String value) {
        for (ProductType productType : values()) {
            if (productType.value.equals(value)) {
                return productType;
            }
        }
        throw new IllegalArgumentException(value);
    }

    @JsonValue
    public String toValue() {
        return value;
    }

}
