package com.memory.Service;

import com.memory.Entity.Bus;
import com.memory.Entity.Configuration;
import com.memory.Entity.Driver;
import com.memory.Entity.TimeInterval;
import com.memory.Utils.DriverType;
import com.memory.Utils.Route;
import com.memory.Utils.TimeIntervalState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Simulation {
    public static final int TIME_SLOTS_PER_DAY = 126; // кол-во элементов в массиве иллюстрирует интервалы времени
    // с 6:00 утра до 3:00 ночи по 10 минут
    public static final int startDayTime = 360;
    public static final int offPeakUsage = 6; // использование автобуса не в час пик
    // (абстрактная величина - 6 вышло и 6 вошло)
    public static final int peakUsage = 15; // использование автобуса в час пик
    // (абстрактная величина - 15 вышло и 15 вошло)
    static int totalTransportedPassengers; // всего перевезённых пассажиров
    static int finishedRoutes; // всего закрытых маршрутов
    public static final int[] nightTime = {
            180, 350
    };
    public static final int[] peakHours = {
            420, 540, 1020, 1140
    };

    public static boolean isPeakTime(int minutes){
        return (minutes >= 420 && minutes <= 540) || (minutes >= 1020 && minutes <= 1140);
    }
    public static boolean isNightTime(int minutes){
        return (minutes >= 180 && minutes <= 350);
    }
    public static TimeIntervalState getTimeInterval(int minutes){
        if (minutes >= 180 && minutes <= 350){
            return TimeIntervalState.NIGHT_TIME;
        } else if ((minutes >= 420 && minutes < 540) || (minutes >= 1020 && minutes < 1140)){
            return TimeIntervalState.PEAK_TIME;
        } else {
            return TimeIntervalState.DEFAULT_TIME;
        }
    }

    public static void main(String[] args) {
        // Создаём водителя типа A
        Driver driverA = new Driver("Иван");
        driverA.setDriverType(DriverType.A);

        // Генерируем маршрут для водителя
        Route routeA = new Route(driverA.getDriverType(), Simulation.startDayTime);
        driverA.setRoute(routeA);

        // Печатаем расписание водителя
        System.out.println("Расписание для водителя " + driverA.getName() + ":");
        driverA.getRoute().printSchedule();

        // Создаём водителя типа B
        Driver driverB = new Driver("Алексей");
        driverB.setDriverType(DriverType.B);

        // Генерируем маршрут для водителя
        Route routeB = new Route(driverB.getDriverType(), Simulation.startDayTime);
        driverB.setRoute(routeB);

        // Печатаем расписание водителя
        System.out.println("\nРасписание для водителя " + driverB.getName() + ":");
        driverB.getRoute().printSchedule();
    }
    public static int calculatePassengerForRoute(Route route){
        int transportedPassengers = 0;
        TimeInterval[] ti = route.getSchedule();
        for (int i = 0; i < ti.length; i++){
            TimeIntervalState t = ti[i].getState();
            switch (t){
                case DEFAULT_TIME -> transportedPassengers += offPeakUsage;
                case PEAK_TIME -> transportedPassengers += peakUsage;
                case NIGHT_TIME -> { }
            }
        }
        return transportedPassengers;
    }
    public static int calculatePassengerForRoute(Driver driver){
        int transportedPassengers = 0;
        TimeInterval[] ti = driver.getRoute().getSchedule();
        for (int i = 0; i < ti.length; i++){
            TimeIntervalState t = ti[i].getState();
            switch (t){
                case DEFAULT_TIME -> transportedPassengers += offPeakUsage;
                case PEAK_TIME -> transportedPassengers += peakUsage;
                case NIGHT_TIME -> { }
            }
        }
        return transportedPassengers;
    }

    public void applyConfiguration(Configuration config){

    }
}