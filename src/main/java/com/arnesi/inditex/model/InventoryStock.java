package com.arnesi.inditex.model;

/**
 * InventoryStock: POJO file used as temporary model for
 * cross-referencing ProductSize and StockEntry data.
 *
 * @Autor Martin Arnesi
 */
public class InventoryStock implements Comparable<InventoryStock> {
    private final int id;
    private final String sizeSystem;
    private final int quantity;

    public InventoryStock(int id, String sizeSystem, int quantity) {
        this.id = id;
        this.sizeSystem = sizeSystem;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public String getSizeSystem() {
        return sizeSystem;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return Integer.toString(this.id);
    }

    @Override
    public int compareTo(InventoryStock o) {
        return this.id - o.getId();
    }
}