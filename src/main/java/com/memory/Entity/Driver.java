package com.memory.Entity;

import com.memory.Utils.DriverType;
import com.memory.Utils.Route;

public class Driver {
    private final String name;
    private boolean hasShiftEnded;
    private DriverType driverType;
    private Route route;


    public Driver(String driverName, DriverType driverType, Route route) {
        this.name = driverName;
        this.driverType = driverType;

    }
    public String getName() {
        return name;
    }
    private static final String[] DRIVER_NAMES = {
            "Алексей",
            "Дмитрий",
            "Екатерина",
            "Марина",
            "Сергей",
            "Али",
            "Джамал",
            "Надежда"
    };
    private Route generateRoute(int timeIntervalIndex) {// управление сигнатурой в твоём распоряжении
        // todo реализовать подачу случайного расписания согласно условию
        return generatedRoute;
    }
}
