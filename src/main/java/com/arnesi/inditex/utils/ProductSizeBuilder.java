package com.arnesi.inditex.utils;

import com.arnesi.inditex.model.ProductSize;

public final class ProductSizeBuilder {
    private int id;
    private String sizeSystem;

    private ProductSizeBuilder() {
    }

    public static ProductSizeBuilder aProductSize() {
        return new ProductSizeBuilder();
    }

    public ProductSizeBuilder withId(int id) {
        this.id = id;
        return this;
    }

    public ProductSizeBuilder withSizeSystem(String sizeSystem) {
        this.sizeSystem = sizeSystem;
        return this;
    }

    public ProductSize build() {
        return new ProductSize(id, sizeSystem);
    }
}