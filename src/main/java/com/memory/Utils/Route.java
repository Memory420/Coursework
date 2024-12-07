package com.memory.Utils;

import com.memory.Entity.TimeInterval;
import com.memory.Service.Simulation;

/**
 * Класс {@code Route} представляет собой класс с полем отвечающим за
 * расписание и то что происходит в каждом промежутке времени в этом расписании
 */
public class Route {
    private TimeInterval[] schedule;

    public Route(DriverType driverType, int startWorkTime) {
        int workingHours = 0;
        switch (driverType) {
            case A -> workingHours = 9;
            case B -> workingHours = 24; // полные рабочие сутки
        }
        schedule = new TimeInterval[6 * workingHours];
        for (TimeInterval timeInterval : schedule) {
            for (int i = 0; i < workingHours * 6; i++) {
                int minutes = (startWorkTime + i * 10) % 1440;
                if (Simulation.isNightTime(minutes)) {
                    schedule[i] = new TimeInterval(minutes, TimeIntervalState.NIGHT_TIME);
                } else {
                    schedule[i] = new TimeInterval(minutes, TimeIntervalState.WORKING_TIME);
                }

            }
        }
    }
    public void printSchedule() {
        for (TimeInterval timeInterval : schedule) {
            System.out.println(timeInterval.prettyPrint());
        }
    }
}
