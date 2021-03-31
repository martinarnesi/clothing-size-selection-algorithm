package com.arnesi.inditex.utils;

import com.arnesi.inditex.model.StockEntry;

public final class StockEntryBuilder {
    private int sizeId;
    private int quantity;

    private StockEntryBuilder() {
    }

    public static StockEntryBuilder aStockEntry() {
        return new StockEntryBuilder();
    }

    public StockEntryBuilder withSizeId(int sizeId) {
        this.sizeId = sizeId;
        return this;
    }

    public StockEntryBuilder withQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public StockEntry build() {
        return new StockEntry(sizeId, quantity);
    }
}