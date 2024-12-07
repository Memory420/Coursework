package com.memory.Service;

import com.memory.Entity.Bus;

/**
 * Класс {@code BusPark} представляет собой парк автобусов.
 * Он содержит информацию о текущем количестве автобусов и их состоянии.
 */
public class BusPark {
    public static final int MAX_BUSES = 8;         // Максимальное количество автобусов в парке
    private int currentBusCount = 0;               // Текущее количество автобусов в парке
    private final Bus[] buses;                      // Массив автобусов

    public static void main(String[] args) {
        BusPark busPark = new BusPark();
        busPark.checkStatus();
    }

    /**
     * Конструктор класса {@code BusPark}.
     * Инициализирует массив автобусов и устанавливает текущее количество.
     */
    public BusPark() {
        currentBusCount = MAX_BUSES;
        buses = new Bus[MAX_BUSES];
        for (int i = 0; i < MAX_BUSES; i++) {
            buses[i] = new Bus(i + 1);
        }
    }

    /**
     * Возвращает текущее количество автобусов в парке.
     *
     * @return Текущее количество автобусов.
     */
    public int getCurrentBusCount() {
        return currentBusCount;
    }

    /**
     * Проверяет, пуст ли парк автобусов.
     *
     * @return {@code true}, если парк пуст; {@code false} в противном случае.
     */
    public boolean isEmpty() {
        return currentBusCount == 0;
    }

    /**
     * Проверяет статус каждого автобуса в парке и выводит информацию на экран.
     */
    public void checkStatus() {
        for (Bus bus : buses) {
            System.out.println(bus.toString());
        }
    }
}
