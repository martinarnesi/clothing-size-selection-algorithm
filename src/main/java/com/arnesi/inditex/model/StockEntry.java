package com.arnesi.inditex.model;

/**
 * StockEntry: POJO file that represents the inventory of a size.
 * Contains an "sizeId", the same field of the "ProductSize.id"
 * and the "quantity" field representing the quantity in stock for that size.
 *
 * @Autor Martin Arnesi
 */
public class StockEntry {

    private final int sizeId;
    private final int quantity;

    public StockEntry(int sizeId, int quantity) {
        this.sizeId = sizeId;
        this.quantity = quantity;
    }

    public int getSizeId() {
        return sizeId;
    }

    public int getQuantity() {
        return quantity;
    }
}