package com.memory.Service;

import com.memory.Entity.Bus;
import com.memory.Entity.Driver;
import com.memory.Utils.DriverType;
import com.memory.Utils.Route;

/**
 * Класс {@code BusPark} представляет собой парк автобусов.
 * Он содержит информацию о текущем количестве автобусов и их состоянии.
 */
public class BusPark {
    public static final int MAX_BUSES = 8;         // Максимальное количество автобусов в парке
    private int currentBusCount = 0;               // Текущее количество автобусов в парке
    private final Bus[] buses;                      // Массив автобусов


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
    public void assignDriver(Driver driver){
        for (int i = 0; i < MAX_BUSES; i++) {
            Bus currentBus = buses[i];
            if (!currentBus.isOnRoute()){
                driver.setHisBus(currentBus);
                currentBus.setDriver(driver);
                currentBus.setOnRoute(true);
                currentBusCount = getFreeBusCount();
                return;
            }
        }
    }


    private int getFreeBusCount(){
        int freeBusCount = 0;
        for (Bus bus : buses) {
            if (!bus.isOnRoute()){
                freeBusCount++;
            }
        }
        return freeBusCount;
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

    public static void main(String[] args) {
        // Создаём водителя типа B
        Driver driver = new Driver("Василий");
        driver.setDriverType(DriverType.A);

        // Генерируем маршрут для водителя
        Route route = new Route(driver.getDriverType(), Simulation.startDayTime);
        driver.setRoute(route);

        // Печатаем расписание водителя
        System.out.println("Расписание для водителя " + driver.getName() + ":");
        driver.getRoute().printSchedule();
    }
}
