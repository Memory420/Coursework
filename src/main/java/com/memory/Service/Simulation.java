package com.memory.Service;

import com.memory.Entity.TimeInterval;
import com.memory.Utils.DriverType;
import com.memory.Utils.Route;

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


    public void tick(){ // сдвигаемся на 10 минут оп таймлайну, должны происходить процессы
        // todo реализовать симуляцию сдвига на 10 минут с просчётом последствий
    }

    public static boolean isPeakTime(int minutes){
        return (minutes >= 420 && minutes <= 540) || (minutes >= 1020 && minutes <= 1140);
    }
    public static boolean isNightTime(int minutes){
        return (minutes >= 180 && minutes <= 350);
    }
    public static void main(String[] args) {
        BusPark busPark = new BusPark();
        DriverManager driverManager = new DriverManager();
        Route route = new Route(DriverType.A, startDayTime);
        route.printSchedule();
    }
}