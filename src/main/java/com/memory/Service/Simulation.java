package com.memory.Service;

import com.memory.Entity.TimeInterval;

public class Simulation {
    public static final int TIME_SLOTS_PER_DAY = 126; // кол-во элементов в массиве иллюстрирует интервалы времени с 6:00 утра до 3:00 ночи по 10 минут
    public static final int offPeakUsage = 6; // использование автобуса не в час пик (абстрактная величина - 6 вышло и 6 вошло)
    public static final int peakUsage = 15; // использование автобуса в час пик (абстрактная величина - 15 вышло и 15 вошло)
    static int totalTransportedPassengers; // всего перевезённых пассажиров
    static int finishedRoutes; // всего закрытых маршрутов

    public void tick(){ // сдвигаемся на 10 минут оп таймлайну, должны происходить процессы
        // todo реализовать симуляцию сдвига на 10 минут с просчётом последствий
    }
    public static void main(String[] args) {
        TimeInterval timeInterval = new TimeInterval(630);
        System.out.println(timeInterval.prettyPrint());
    }
}