package com.memory.Entity;

import com.memory.Utils.Route;

import java.util.*;

public class Configuration {

    private List<Bus> buses; // Список автобусов
    private List<Driver> drivers; // Список водителей
    private Map<Driver, Route> driverSchedules; // Индивидуальные маршруты для каждого водителя
    private Map<Bus, Driver> busAssignments; // Назначенные водители для автобусов

    public Configuration(int busCount) {
        Random rand = new Random();

    }

    public Configuration(List<Bus> buses, List<Driver> drivers, Map<Driver, Route> driverSchedules, Map<Bus, Driver> busAssignments) {
        this.buses = buses;
        this.drivers = drivers;
        this.driverSchedules = driverSchedules;
        this.busAssignments = busAssignments;
    }

    public void printConfiguration() {

    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Configuration other = (Configuration) obj;
        return buses.equals(other.buses) &&
                drivers.equals(other.drivers) &&
                busAssignments.equals(other.busAssignments) &&
                driverSchedules.equals(other.driverSchedules);
    }

    @Override
    public int hashCode() {
        return Objects.hash(buses, drivers, busAssignments, driverSchedules, driverSchedules);
    }

    public List<Bus> getBuses() {
        return buses;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public Map<Driver, Route> getDriverSchedules() {
        return driverSchedules;
    }

    public Map<Bus, Driver> getBusAssignments() {
        return busAssignments;
    }
}
