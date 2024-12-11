package com.memory.Utils;

import com.memory.Entity.TimeInterval;
import com.memory.Service.Simulation;

/**
 * Класс {@code Route} представляет маршрут с учётом продолжительности каждого маршрута.
 */
public class Route {
    private static final int ROUTE_DURATION_MINUTES = 70; // Продолжительность одного маршрута в минутах
    private TimeInterval[] schedule; // Расписание маршрутов

    public Route(DriverType driverType, int startWorkTime) {
        int workingHours = (driverType == DriverType.A) ? 9 : 24; // Рабочие часы
        int workingMinutes = workingHours * 60; // Общее количество рабочих минут
        int totalIntervals = (workingMinutes + ROUTE_DURATION_MINUTES - 1) / 10; // Количество интервалов (учитываем маршрут)

        schedule = new TimeInterval[totalIntervals];
        int currentMinutes = startWorkTime;

        boolean previousBreak = false; // Флаг для проверки предыдущего перерыва
        int timeSinceLastBreak = 0; // Время с последнего перерыва

        for (int i = 0; i < totalIntervals; i++) {
            TimeIntervalState state = Simulation.getTimeInterval(currentMinutes);

            // Добавляем перерывы для водителей типа B
            if (driverType == DriverType.B && timeSinceLastBreak >= 120 && !previousBreak) {
                schedule[i] = new TimeInterval(currentMinutes, TimeIntervalState.BREAK_TIME);
                previousBreak = true;
                timeSinceLastBreak = 0; // Сбрасываем время с последнего перерыва
            } else if (driverType == DriverType.A && i % 36 == 0 && state != TimeIntervalState.PEAK_TIME) {
                // Добавляем обед для водителей типа A
                schedule[i] = new TimeInterval(currentMinutes, TimeIntervalState.LUNCH_BREAK);
                previousBreak = false;
            } else {
                // Добавляем маршрут
                schedule[i] = new TimeInterval(currentMinutes, state);
                previousBreak = false;
                timeSinceLastBreak += 10;
            }

            // Увеличиваем время на 10 минут
            currentMinutes = (currentMinutes + 10) % 1440;
        }
    }

    public TimeInterval[] getSchedule() {
        return schedule;
    }

    public static TimeInterval[] generateSchedule(DriverType driverType, int startWorkTime) {
        int workingHours = (driverType == DriverType.A) ? 9 : 24; // Рабочие часы
        int workingMinutes = workingHours * 60; // Общее количество рабочих минут
        int totalIntervals = (workingMinutes + ROUTE_DURATION_MINUTES - 1) / 10; // Количество интервалов (учитываем маршрут)

        TimeInterval[] schedule = new TimeInterval[totalIntervals];
        int currentMinutes = startWorkTime;

        boolean previousBreak = false; // Флаг для проверки предыдущего перерыва
        int timeSinceLastBreak = 0; // Время с последнего перерыва

        for (int i = 0; i < totalIntervals; i++) {
            TimeIntervalState state = Simulation.getTimeInterval(currentMinutes);

            // Добавляем перерывы для водителей типа B
            if (driverType == DriverType.B && timeSinceLastBreak >= 120 && !previousBreak) {
                schedule[i] = new TimeInterval(currentMinutes, TimeIntervalState.BREAK_TIME);
                previousBreak = true;
                timeSinceLastBreak = 0; // Сбрасываем время с последнего перерыва
            } else if (driverType == DriverType.A && i % 36 == 0 && state != TimeIntervalState.PEAK_TIME) {
                // Добавляем обед для водителей типа A
                schedule[i] = new TimeInterval(currentMinutes, TimeIntervalState.LUNCH_BREAK);
                previousBreak = false;
            } else {
                // Добавляем маршрут
                schedule[i] = new TimeInterval(currentMinutes, state);
                previousBreak = false;
                timeSinceLastBreak += 10;
            }

            // Увеличиваем время на 10 минут
            currentMinutes = (currentMinutes + 10) % 1440;
        }
        return schedule;
    }

    public void printSchedule() {
        for (TimeInterval timeInterval : schedule) {
            System.out.println(timeInterval.prettyPrint());
        }
    }
}
