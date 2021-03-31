package com.arnesi.inditex.application;

import com.arnesi.inditex.service.WarehouseManagementSystem;

/**
 * Size Selection Algorithm - Application Start Point
 *
 * @Author Martin Arnesi 03/22/2021
 */
public class Application {
    public static void main(String[] args) {
        WarehouseManagementSystem wms = new WarehouseManagementSystem();
        System.out.println(wms.checkStockAvailability());

        System.out.println("\n\nPor favor mira mis comentarios sobre el algoritmo " +
                "dentro de la clase WarehouseManagementSystem. :D");
    }
}
