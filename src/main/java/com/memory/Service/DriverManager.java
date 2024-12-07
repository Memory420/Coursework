package com.memory.Service;

import com.memory.Entity.Driver;
import com.memory.Utils.DriverType;
import com.memory.Utils.Route;

/**
 * Класс {@code DriverManager} управляет водителями, их количеством и инициализацией.
 * Он отвечает за создание водителей и хранение информации о свободных водителях.
 */
public class DriverManager {
    private int freeDriversCount = 0; // Количество свободных водителей
    private Driver[] drivers;           // Массив водителей

    /**
     * Конструктор для создания экземпляра класса {@code DriverManager}.
     * Инициализирует массив водителей и устанавливает количество свободных водителей.
     */
    public DriverManager() {
        freeDriversCount = BusPark.MAX_BUSES;
        drivers = new Driver[DRIVER_NAMES.length];

        for (int i = 0; i < DRIVER_NAMES.length; i++) {
            drivers[i] = new Driver(DRIVER_NAMES[i], DriverType.A, new Route());
        }
    }

    private static final String[] DRIVER_NAMES = {
            "Алексей",
            "Пулемётчик",
            "Галим",
            "Мамбет",
            "Сергей",
            "Али",
            "Джамал",
            "Надежда"
    };
}
