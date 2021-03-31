package com.arnesi.inditex.model;

/**
 * ProductSize: POJO file that represents the size of a product.
 * Contains an "id" field (required field for the final solution) and
 * the "sizeSystem" field which can be used to detect equivalences.
 *
 * @Autor MArnesi
 */
public class ProductSize {

    private final int id;
    private final String sizeSystem;

    public ProductSize(int id, String sizeSystem) {
        this.id = id;
        this.sizeSystem = sizeSystem;
    }

    public int getId() {
        return id;
    }

    public String getSizeSystem() {
        return sizeSystem;
    }
}