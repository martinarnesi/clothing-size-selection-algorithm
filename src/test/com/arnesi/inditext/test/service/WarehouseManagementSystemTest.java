package com.arnesi.inditext.test.service;

import com.arnesi.inditex.exception.CreationException;
import com.arnesi.inditex.exception.InvalidListEntryException;
import com.arnesi.inditex.model.InventoryStock;
import com.arnesi.inditex.model.ProductSize;
import com.arnesi.inditex.model.StockEntry;
import com.arnesi.inditex.service.WarehouseManagementSystem;
import com.arnesi.inditex.utils.ProductSizeBuilder;
import com.arnesi.inditex.utils.StockEntryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WarehouseManagementSystemTest {

    private static List<ProductSize> productSizes;
    private static List<StockEntry> stock;

    private WarehouseManagementSystem wms;

    @BeforeEach
    void setUp() {
        wms = new WarehouseManagementSystem();
        productSizes = new ArrayList<>();
        stock = new ArrayList<>();
    }

    @AfterEach
    void tearDown() {
        wms = null;
        productSizes = null;
        stock = null;
    }

    @Test
    @DisplayName("Test method checkStockAvailability - Happy Path normal Scenario 0")
    void checkStockAvailabilityTest() {
        InventoryStock expectedInventoryStock1 = new InventoryStock(2, "123", 13);
        InventoryStock expectedInventoryStock2 = new InventoryStock(3, "157", 12);
        InventoryStock expectedInventoryStock3 = new InventoryStock(8, "899", 35);
        InventoryStock expectedInventoryStock4 = new InventoryStock(9, "4578", 24);

        // checkStockAvailability() method call loadData() with pre-loaded data
        List<InventoryStock> inventoryStocks = wms.checkStockAvailability();

        assertNotNull(inventoryStocks);
        assertFalse(inventoryStocks.isEmpty());

        assertEquals(expectedInventoryStock1.getId(), inventoryStocks.get(0).getId());
        assertEquals(expectedInventoryStock2.getId(), inventoryStocks.get(1).getId());
        assertEquals(expectedInventoryStock3.getId(), inventoryStocks.get(2).getId());
        assertEquals(expectedInventoryStock4.getId(), inventoryStocks.get(3).getId());

        assertEquals(expectedInventoryStock1.getSizeSystem(), inventoryStocks.get(0).getSizeSystem());
        assertEquals(expectedInventoryStock2.getSizeSystem(), inventoryStocks.get(1).getSizeSystem());
        assertEquals(expectedInventoryStock3.getSizeSystem(), inventoryStocks.get(2).getSizeSystem());
        assertEquals(expectedInventoryStock4.getSizeSystem(), inventoryStocks.get(3).getSizeSystem());

        assertEquals(expectedInventoryStock1.getQuantity(), inventoryStocks.get(0).getQuantity());
        assertEquals(expectedInventoryStock2.getQuantity(), inventoryStocks.get(1).getQuantity());
        assertEquals(expectedInventoryStock3.getQuantity(), inventoryStocks.get(2).getQuantity());
        assertEquals(expectedInventoryStock4.getQuantity(), inventoryStocks.get(3).getQuantity());
    }

    @Test
    @DisplayName("Test method runSizeSelectionAlgorithm - Happy Path normal Scenario 1")
    void runSizeSelectionAlgorithmTestHappyPath1() {
        //Product size with 4 elements with 3 with equivalent sizes (expected Id 2 with quantity of 13)
        ProductSize equivalentSize1 = ProductSizeBuilder.aProductSize().withId(1).withSizeSystem("123").build();
        ProductSize equivalentSize2 = ProductSizeBuilder.aProductSize().withId(2).withSizeSystem("213").build();
        ProductSize noEquivalentSize = ProductSizeBuilder.aProductSize().withId(3).withSizeSystem("571").build();
        ProductSize equivalentSize3 = ProductSizeBuilder.aProductSize().withId(4).withSizeSystem("321").build();
        productSizes.addAll(Arrays.asList(equivalentSize1, equivalentSize2, noEquivalentSize, equivalentSize3));

        StockEntry stockEntry1 = StockEntryBuilder.aStockEntry().withSizeId(1).withQuantity(9).build();
        StockEntry stockEntry2 = StockEntryBuilder.aStockEntry().withSizeId(2).withQuantity(13).build();
        StockEntry stockEntry3 = StockEntryBuilder.aStockEntry().withSizeId(3).withQuantity(12).build();
        StockEntry stockEntry4 = StockEntryBuilder.aStockEntry().withSizeId(4).withQuantity(5).build();
        stock.addAll(Arrays.asList(stockEntry1, stockEntry2, stockEntry3, stockEntry4));

        List<InventoryStock> inventoryStocks = wms.runSizeSelectionAlgorithm(productSizes, stock);

        assertNotNull(inventoryStocks);
        assertFalse(inventoryStocks.isEmpty());

        //Equivalent scenario, return ProductSize.Id == 2 with stock of 13 elements
        assertEquals(2, inventoryStocks.get(0).getId());
        assertEquals(13, inventoryStocks.get(0).getQuantity());

        //Normal scenario, return ProductSize.Id == 3 with stock of 12 elements
        assertEquals(3, inventoryStocks.get(1).getId());
        assertEquals(12, inventoryStocks.get(1).getQuantity());
    }

    @Test
    @DisplayName("Test method runSizeSelectionAlgorithm - Happy Path normal Scenario 2")
    void runSizeSelectionAlgorithmTestHappyPath2() {
        //Product size with 6 elements 3 with equivalent sizes, 1 with empty and 1 null (expected Id 2 with quantity of 13)
        ProductSize equivalentSize1 = ProductSizeBuilder.aProductSize().withId(1).withSizeSystem("123").build();
        ProductSize equivalentSize2 = ProductSizeBuilder.aProductSize().withId(2).withSizeSystem("213").build();
        ProductSize withZeroStick = ProductSizeBuilder.aProductSize().withId(3).withSizeSystem("571").build();
        ProductSize equivalentSize3 = ProductSizeBuilder.aProductSize().withId(4).withSizeSystem("321").build();
        ProductSize withNullValue = null;

        productSizes.addAll(Arrays.asList(equivalentSize1, equivalentSize2, equivalentSize3, withZeroStick, withNullValue));

        StockEntry stockEntry1 = StockEntryBuilder.aStockEntry().withSizeId(1).withQuantity(9).build();
        StockEntry stockEntry2 = StockEntryBuilder.aStockEntry().withSizeId(2).withQuantity(13).build();
        StockEntry stockEntry3 = StockEntryBuilder.aStockEntry().withSizeId(3).withQuantity(0).build();
        StockEntry stockEntry4 = StockEntryBuilder.aStockEntry().withSizeId(4).withQuantity(5).build();
        stock.addAll(Arrays.asList(stockEntry1, stockEntry2, stockEntry3, stockEntry4));

        List<InventoryStock> inventoryStocks = wms.runSizeSelectionAlgorithm(productSizes, stock);

        assertNotNull(inventoryStocks);
        assertFalse(inventoryStocks.isEmpty());
        assertEquals(1, inventoryStocks.size());

        // After algorithm processing, we have one element for a equivalent scenario,
        // return ProductSize.Id == 2 with stock of 13 elements.
        // Scenarios with null and with 0 stock are discarded.
        assertEquals(2, inventoryStocks.get(0).getId());
        assertEquals(13, inventoryStocks.get(0).getQuantity());
    }

    @Test
    @DisplayName("Test method runSizeSelectionAlgorithm - Happy Path normal Scenario 3 - sorting result")
    void runSizeSelectionAlgorithmTestHappyPath3() {
        //Product size with 8 elements 3 with equivalent sizes, 1 with empty and 1 null (expected Id 2 with quantity of 13)
        ProductSize equivalentSize1 = ProductSizeBuilder.aProductSize().withId(1).withSizeSystem("123").build();
        ProductSize equivalentSize2 = ProductSizeBuilder.aProductSize().withId(2).withSizeSystem("213").build();
        ProductSize withZeroStick = ProductSizeBuilder.aProductSize().withId(3).withSizeSystem("571").build();
        ProductSize equivalentSize3 = ProductSizeBuilder.aProductSize().withId(4).withSizeSystem("321").build();
        ProductSize withNullValue = null;
        ProductSize normalSize1 = ProductSizeBuilder.aProductSize().withId(5).withSizeSystem("999").build();
        ProductSize normalSize2 = ProductSizeBuilder.aProductSize().withId(6).withSizeSystem("888").build();

        productSizes.addAll(Arrays.asList(equivalentSize1, equivalentSize2, equivalentSize3, withZeroStick,
                withNullValue, normalSize1, normalSize2));

        StockEntry stockEntry1 = StockEntryBuilder.aStockEntry().withSizeId(1).withQuantity(9).build();
        StockEntry stockEntry2 = StockEntryBuilder.aStockEntry().withSizeId(2).withQuantity(13).build();
        StockEntry stockEntry3 = StockEntryBuilder.aStockEntry().withSizeId(3).withQuantity(0).build();
        StockEntry stockEntry4 = StockEntryBuilder.aStockEntry().withSizeId(4).withQuantity(5).build();
        StockEntry stockEntry5 = StockEntryBuilder.aStockEntry().withSizeId(5).withQuantity(33).build();
        StockEntry stockEntry6 = StockEntryBuilder.aStockEntry().withSizeId(6).withQuantity(12).build();
        stock.addAll(Arrays.asList(stockEntry1, stockEntry2, stockEntry3, stockEntry4, stockEntry5, stockEntry6));

        List<InventoryStock> inventoryStocks = wms.runSizeSelectionAlgorithm(productSizes, stock);

        assertNotNull(inventoryStocks);
        assertFalse(inventoryStocks.isEmpty());
        assertEquals(3, inventoryStocks.size());

        // After algorithm processing, we have one element for a equivalent scenario,
        // ProductSize.Id == 2 with stock of 13 elements.
        // Scenarios with null and with 0 stock are discarded.
        // Id return order should be Id's [2, 6, 5] ordered from smallest to largest
        assertEquals(2, inventoryStocks.get(0).getId());
        assertEquals(6, inventoryStocks.get(1).getId());
        assertEquals(5, inventoryStocks.get(2).getId());
    }

    @Test
    @DisplayName("Test method runSizeSelectionAlgorithm - Check sortSizeSystem method")
    void runSizeSelectionAlgorithmTestSortSizeSystem() {

        String expectedSizeSystem = "123456789";
        String unsortedSizeSystem = "918237645";

        //Product size with 1 elements
        productSizes.addAll(Collections.singletonList(ProductSizeBuilder.aProductSize().withId(1).withSizeSystem(unsortedSizeSystem).build()));
        stock.addAll(Collections.singletonList(StockEntryBuilder.aStockEntry().withSizeId(1).withQuantity(9).build()));

        List<InventoryStock> inventoryStocks = wms.runSizeSelectionAlgorithm(productSizes, stock);

        assertNotNull(inventoryStocks);
        assertFalse(inventoryStocks.isEmpty());
        assertEquals(1, inventoryStocks.size());

        assertEquals(1, inventoryStocks.get(0).getId());
        assertEquals(expectedSizeSystem, inventoryStocks.get(0).getSizeSystem());
    }

    @Test
    @DisplayName("Test method runSizeSelectionAlgorithm - Check numerical values only for sortSizeSystem method")
    void runSizeSelectionAlgorithmTestSortSizeSystemOnlyNumerical() {
        String unsortedSizeSystem = "ABC1234";

        //Product size with 1 elements with invalid size system.
        productSizes.addAll(Collections.singletonList(ProductSizeBuilder.aProductSize().withId(1).withSizeSystem(unsortedSizeSystem).build()));
        stock.addAll(Collections.singletonList(StockEntryBuilder.aStockEntry().withSizeId(1).withQuantity(9).build()));

        assertThrows(InvalidListEntryException.class,
                () -> wms.runSizeSelectionAlgorithm(productSizes, stock));
    }

    @Test
    @DisplayName("Test method runSizeSelectionAlgorithm - productSizes list is null")
    void runSizeSelectionAlgorithmWithNulProductSizesList() {

        productSizes = null;

        StockEntry stockEntry = StockEntryBuilder.aStockEntry().withSizeId(1).withQuantity(9).build();
        stock.addAll(Collections.singletonList(stockEntry));

        assertThrows(CreationException.class,
                () -> wms.runSizeSelectionAlgorithm(productSizes, stock));
    }

    @Test
    @DisplayName("Test method runSizeSelectionAlgorithm - stock list is null")
    void runSizeSelectionAlgorithmWithNulStockList() {
        ProductSize productSize = ProductSizeBuilder.aProductSize().withId(1).withSizeSystem("321").build();
        productSizes.addAll(Collections.singletonList(productSize));

        stock = null;

        assertThrows(CreationException.class,
                () -> wms.runSizeSelectionAlgorithm(productSizes, stock));
    }

    @Test
    @DisplayName("Test method runSizeSelectionAlgorithm - Both input lists are null")
    void runSizeSelectionAlgorithmEntryListsAreNull() {

        productSizes = null;
        stock = null;

        assertThrows(CreationException.class,
                () -> wms.runSizeSelectionAlgorithm(productSizes, stock));
    }

    @Test
    @DisplayName("Test method runSizeSelectionAlgorithm - productSizes list is empty")
    void runSizeSelectionAlgorithmWithEmptyProductSizesList() {

        productSizes = Collections.emptyList();

        StockEntry stockEntry = StockEntryBuilder.aStockEntry().withSizeId(1).withQuantity(9).build();
        stock.addAll(Collections.singletonList(stockEntry));

        assertThrows(InvalidListEntryException.class,
                () -> wms.runSizeSelectionAlgorithm(productSizes, stock));
    }

    @Test
    @DisplayName("Test method runSizeSelectionAlgorithm - stock list is empty")
    void runSizeSelectionAlgorithmWithEmptyStockList() {

        ProductSize productSize = ProductSizeBuilder.aProductSize().withId(1).withSizeSystem("321").build();
        productSizes.addAll(Collections.singletonList(productSize));

        stock = Collections.emptyList();

        assertThrows(InvalidListEntryException.class,
                () -> wms.runSizeSelectionAlgorithm(productSizes, stock));
    }

    @Test
    @DisplayName("Test method runSizeSelectionAlgorithm - Both input lists are empty")
    void runSizeSelectionAlgorithmEntryListsAreEmpty() {

        productSizes = Collections.emptyList();
        stock = Collections.emptyList();

        assertThrows(InvalidListEntryException.class,
                () -> wms.runSizeSelectionAlgorithm(productSizes, stock));
    }
}