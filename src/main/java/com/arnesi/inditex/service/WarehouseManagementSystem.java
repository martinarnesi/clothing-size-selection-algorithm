package com.arnesi.inditex.service;

import com.arnesi.inditex.exception.CreationException;
import com.arnesi.inditex.exception.InvalidListEntryException;
import com.arnesi.inditex.model.InventoryStock;
import com.arnesi.inditex.model.ProductSize;
import com.arnesi.inditex.model.StockEntry;
import com.arnesi.inditex.utils.PopulateUtil;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.*;

public class WarehouseManagementSystem {
    private static List<ProductSize> productSizes;
    private static List<StockEntry> stock;
    private static List<InventoryStock> cacheInventoryStock;

    public WarehouseManagementSystem() {
        productSizes = new ArrayList<>();
        stock = new ArrayList<>();
        cacheInventoryStock = new ArrayList<>();
    }

    public List<InventoryStock> checkStockAvailability() {
        //Populate List
        PopulateUtil.loadData(productSizes, stock);

        // Execute selection algorithm
        return runSizeSelectionAlgorithm(productSizes, stock);
    }

    /**
     * Explicación en castellano del Algoritmo de Selección de Tallas
     * **************************************************************
     * La primera versión de este programa, buscaba recorrer las 2 listas (productSizes y stock) utilizando 2 bucles FOR
     * anidados, para así obtener los datos cruzados entre el SKU (sizeSystem) y el numero de stock (quantity), simulando
     * un “inner join” de 2 tablas (listas) y creando asi un tipo de “cache” con la clase InventoryStock.
     *
     * En esta primera versión busque solucionar el problema en forma rápida, pero, no era performante y tenia posibilidad
     * de mejora, ya que al utilizar este forma de iteración, obtenía un complejidad algorítmica de tipo O(N^2).
     *
     * En la segunda versión, combine simples iteraciones de las listas con un HashMap, con lo cual reduje la complejidad
     * temporal a O(n) para el FOR y O(1) para el GET y PUT del Mapa, con lo cual se transformó en O(n + 1) ==> O(n).
     *
     * ¿Consideras que se podría mejorar de alguna manera?
     * Considero que siempre se puede mejorar. Podria haber utilizado una Set, y crear un algoritmo que compare los
     * sizeSystem equivalentes y a su vez guarde el de mayor stock. tendria un conjunto mas reducido en
     * cantidad de elementes ya que el SET no permite duplicados. Por falta de tiempo en estos momentos, opte por la
     * opcion que les estoy enviando.
     *
     * PD: Pido disculpas por el codigo y comentarios en Ingles, es ya costumbre por mi trabajo dia a dia y me resulto
     * raro poner nombres y comentarios en castellano.
     *
     * Saludos, Martin Arnesi
     *
     *
     * @param productSizes List
     * @param stock List
     * @return List<InventoryStock>
     */
    public List<InventoryStock> runSizeSelectionAlgorithm(List<ProductSize> productSizes, List<StockEntry> stock) {
        if(Objects.isNull(productSizes) || Objects.isNull(stock)) {
            throw new CreationException("Input lists should not be null");
        }

        if(productSizes.isEmpty() || stock.isEmpty()) {
            throw new InvalidListEntryException("Input lists should not be empty");
        }

        Map<Integer, StockEntry> stockMap = new HashMap<>();
        Map<String, InventoryStock> processedMap = new HashMap<>();

        // O(n + 1) ==> O(n)
        for (StockEntry stockEntry : stock) {
            if (Objects.nonNull(stockEntry) && stockEntry.getQuantity() != 0) {
                stockMap.put(stockEntry.getSizeId(), stockEntry);
            }
        }

        // O(n + 1) = O(n)
        for (ProductSize productSize : productSizes) {
            if(Objects.isNull(productSize)) {
                continue;
            }
            StockEntry stockEntry = stockMap.get(productSize.getId());

            if (Objects.nonNull(stockEntry)) {
                InventoryStock inventoryStock = new InventoryStock(
                        productSize.getId(),
                        sortSizeSystem(productSize.getSizeSystem()),
                        stockEntry.getQuantity());

                // Equivalent size scenario
                checkEquivalentSizes(processedMap, stockEntry, inventoryStock);
            }
        }

        // Sorting Id's for selected sizes from smallest to largest, separated by commas.
        List<InventoryStock> inventoryStocks = new ArrayList<>(processedMap.values());
        Collections.sort(new ArrayList<>(processedMap.values()));

        return inventoryStocks;
    }

    /**
     * Helper method to check equivalent sizes.
     * Replace the existing entry in the map if the Stock Quantity is bigger
     * and have the same SizeSystem as at key.
     *
     * @param processedMap
     * @param stockEntry
     * @param inventoryStock
     */
    private void checkEquivalentSizes(Map<String, InventoryStock> processedMap, StockEntry stockEntry, InventoryStock inventoryStock) {
        if (processedMap.containsKey(inventoryStock.getSizeSystem())) {
            if (stockEntry.getQuantity() > processedMap.get(inventoryStock.getSizeSystem()).getQuantity()) {
                processedMap.put(inventoryStock.getSizeSystem(), inventoryStock);
            }
        } else {
            processedMap.put(inventoryStock.getSizeSystem(), inventoryStock);
        }
    }

    /**
     * Helper method to reorganize the characters on the size system variable.
     *
     * @param sizeSystem a sizeSystem String
     * @return a sorted sizeSystem String
     */
    private static String sortSizeSystem(String sizeSystem) {
        if (!NumberUtils.isCreatable(sizeSystem)) {
            throw new InvalidListEntryException("Only numerical values are allowed for the size system");
        }

        return sizeSystem.chars()
                .sorted()
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}