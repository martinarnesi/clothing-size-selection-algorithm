package com.arnesi.inditex.utils;

import com.arnesi.inditex.exception.CreationException;
import com.arnesi.inditex.model.ProductSize;
import com.arnesi.inditex.model.StockEntry;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class PopulateUtil {

    private PopulateUtil() {

    }

    /**
     * Method used to populate lists (ProductSize and StockEntry) with the information needed for testing.
     *
     * @param productSizes empty productSizes List
     * @param stock        empty stock List
     */
    public static void loadData(List<ProductSize> productSizes, List<StockEntry> stock) {

        if (Objects.isNull(productSizes) || Objects.isNull(stock)) {
            throw new CreationException("Uninitialized input lists - Unable to proceed with the lists filling");
        }

        ProductSize clothing1 = ProductSizeBuilder.aProductSize().withId(1).withSizeSystem("123").build(); //equivalent size 1
        ProductSize clothing2 = ProductSizeBuilder.aProductSize().withId(2).withSizeSystem("213").build(); //equivalent size 1
        ProductSize clothing3 = ProductSizeBuilder.aProductSize().withId(3).withSizeSystem("571").build();
        ProductSize clothing4 = ProductSizeBuilder.aProductSize().withId(4).withSizeSystem("321").build(); //equivalent size 1
        ProductSize clothing5 = ProductSizeBuilder.aProductSize().withId(5).withSizeSystem("359").build(); //out-of-stock scenario.
        ProductSize clothing6 = ProductSizeBuilder.aProductSize().withId(6).withSizeSystem("666").build(); //null stock scenario
        ProductSize clothing7 = ProductSizeBuilder.aProductSize().withId(7).withSizeSystem("989").build(); //equivalent size 2
        ProductSize clothing8 = ProductSizeBuilder.aProductSize().withId(8).withSizeSystem("899").build(); //equivalent size 2
        ProductSize clothing9 = ProductSizeBuilder.aProductSize().withId(9).withSizeSystem("7845").build();

        productSizes.addAll(
                Arrays.asList(
                        clothing1, clothing2, clothing3, clothing4, clothing5,
                        clothing6, clothing7, clothing8, clothing9)
        );

        StockEntry stockEntry1 = StockEntryBuilder.aStockEntry().withSizeId(1).withQuantity(9).build();
        StockEntry stockEntry2 = StockEntryBuilder.aStockEntry().withSizeId(2).withQuantity(13).build();
        StockEntry stockEntry3 = StockEntryBuilder.aStockEntry().withSizeId(3).withQuantity(12).build();
        StockEntry stockEntry4 = StockEntryBuilder.aStockEntry().withSizeId(4).withQuantity(5).build();
        StockEntry stockEntry5 = StockEntryBuilder.aStockEntry().withSizeId(5).withQuantity(0).build(); // out-of-stock scenario.
        StockEntry stockEntry6 = null; //null stock scenario
        StockEntry stockEntry7 = StockEntryBuilder.aStockEntry().withSizeId(7).withQuantity(25).build();
        StockEntry stockEntry8 = StockEntryBuilder.aStockEntry().withSizeId(8).withQuantity(35).build();
        StockEntry stockEntry9 = StockEntryBuilder.aStockEntry().withSizeId(9).withQuantity(24).build();

        stock.addAll(
                Arrays.asList(
                        stockEntry1, stockEntry2, stockEntry3, stockEntry4, stockEntry5,
                        stockEntry6, stockEntry7, stockEntry8, stockEntry9));
    }
}