package com.arnesi.inditext.test.util;

import com.arnesi.inditex.exception.CreationException;
import com.arnesi.inditex.model.ProductSize;
import com.arnesi.inditex.model.StockEntry;
import com.arnesi.inditex.utils.PopulateUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PopulateUtilTest {

    @Test
    @DisplayName("Test loadData method - Happy Path - Populate List OK")
    void loadDataTest() {
        List<ProductSize> productSizes = new ArrayList<>();
        List<StockEntry> stock = new ArrayList<>();

        PopulateUtil.loadData(productSizes, stock);

        assertNotNull(productSizes);
        assertNotNull(stock);
        assertFalse(productSizes.isEmpty());
        assertFalse(stock.isEmpty());
        assertEquals(9, productSizes.size());
        assertEquals(9, stock.size());
    }

    @Test
    @DisplayName("Test loadData method - Null productSize List")
    void loadDataTestNullProductSizeList() {
        List<ProductSize> productSizes = null;
        List<StockEntry> stock = new ArrayList<>();

        assertThrows(CreationException.class,
                () -> PopulateUtil.loadData(productSizes, stock));
    }

    @Test
    @DisplayName("Test loadData method - Null stock List")
    void loadDataTestNullStockList() {
        List<ProductSize> productSizes = new ArrayList<>();
        List<StockEntry> stock = null;

        assertThrows(CreationException.class,
                () -> PopulateUtil.loadData(productSizes, stock));
    }
}