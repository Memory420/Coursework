package com.memory.Entity;

import com.memory.Utils.DriverType;
import com.memory.Utils.Route;

public class Driver {
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

    private void generateRoute(int startWorkTime) {

    }
}
