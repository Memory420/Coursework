package com.memory.Entity;

import com.memory.Utils.DriverType;
import com.memory.Utils.Route;
import com.memory.Entity.TimeInterval;

public class Driver {


    private Bus hisBus;
    private final String name;
    private boolean hasShiftEnded;
    private DriverType driverType;
    private Route route;
    private int workedMins;
    private int lastBreakTime;

    public Driver(String driverName) {
        this.name = driverName;
    }

    public String getName() {
        return name;
    }

    public void setDriverType(DriverType driverType) {
        this.driverType = driverType;
    }

    public DriverType getDriverType() {
        return driverType;
    }

    // Геттер для маршрута
    public Route getRoute() {
        return route;
    }

    // Метод для назначения маршрута водителю
    public void setRoute(Route route) {
        this.route = route;
    }

    // Генерация расписания водителя на основе его маршрута
    public TimeInterval[] getDriverSchedule(int startWorkTime) {
        if (route != null) {
            return route.generateSchedule(driverType, startWorkTime);
        }
        return new TimeInterval[0];  // Возвращаем пустой массив, если маршрута нет
    }

    public Bus getHisBus() {
        return hisBus;
    }

    public void setHisBus(Bus hisBus) {
        this.hisBus = hisBus;
    }
}
