package com.memory;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Главный класс системы расписания автобусов.
 * Содержит метод {@code main}, который инициализирует данные, запускает алгоритмы планирования
 * и выводит результаты.
 */
public class BusScheduling {
    /**
     * Инициализирует данные, запускает жадный и генетический алгоритмы планирования,
     * выводит статистику и детализированные назначения, а также считает конфликты.
     */
    public static void main(String[] args) {
        // Инициализация данных
        List<Bus> buses = initializeBuses(8);
        List<Driver> drivers = initializeDrivers();
        List<Route> routes = initializeRoutes();

        // Жадный алгоритм
        long startGreedy = System.currentTimeMillis();
        GreedyScheduler greedyScheduler = new GreedyScheduler(cloneBuses(buses), cloneDrivers(drivers), cloneRoutes(routes));
        Schedule greedySchedule = greedyScheduler.createSchedule();
        long endGreedy = System.currentTimeMillis();

        // Генетический алгоритм
        long startGenetic = System.currentTimeMillis();
        GeneticSchedularGA geneticScheduler = new GeneticSchedularGA(cloneBuses(buses), cloneDrivers(drivers),
                cloneRoutes(routes), 50, 100, 0.05);
        Schedule geneticSchedule = geneticScheduler.createSchedule();
        long endGenetic = System.currentTimeMillis();

        // Вывод статистики
        System.out.println("Жадный алгоритм:");
        System.out.println("Время выполнения: " + (endGreedy - startGreedy) + " мс");
        System.out.println("Количество назначенных маршрутов: " + greedySchedule.getAssignments().size());

        System.out.println("\nГенетический алгоритм:");
        System.out.println("Время выполнения: " + (endGenetic - startGenetic) + " мс");
        System.out.println("Количество назначенных маршрутов: " + geneticSchedule.getAssignments().size());

        // Вывод детализированных назначений
        printAssignments("Жадного алгоритма", greedySchedule);
        printAssignments("Генетического алгоритма", geneticSchedule);

        // Подсчёт и вывод конфликтов
        int conflictsGreedy = calculateConflicts(greedySchedule);
        int conflictsGenetic = calculateConflicts(geneticSchedule);

        System.out.println("\nКонфликты Жадного алгоритма: " + conflictsGreedy);
        System.out.println("Конфликты Генетического алгоритма: " + conflictsGenetic);
    }

    /**
     * Инициализирует список автобусов с указанным количеством.
     *
     * @param count Количество автобусов для инициализации.
     * @return Список инициализированных автобусов.
     */
    private static List<Bus> initializeBuses(int count) {
        List<Bus> buses = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            buses.add(new Bus(i));
        }
        return buses;
    }

    /**
     * Инициализирует список водителей, распределяя их по типам A и B.
     *
     * @return Список инициализированных водителей.
     */
    private static List<Driver> initializeDrivers() {
        List<Driver> drivers = new ArrayList<>();
        // Инициализация водителей типов A и B
        for (int i = 1; i <= 10; i++) {
            drivers.add(new Driver(i, Driver.DriverType.TYPE_A));
        }
        for (int i = 11; i <= 20; i++) {
            drivers.add(new Driver(i, Driver.DriverType.TYPE_B));
        }
        return drivers;
    }

    /**
     * Инициализирует список маршрутов, добавляя в каждый маршрут временные слоты.
     * Временные слоты создаются с интервалом 1 час 10 минут, начиная с 06:00 до 03:00 следующего дня.
     *
     * @return Список инициализированных маршрутов.
     */
    private static List<Route> initializeRoutes() {
        List<Route> routes = new ArrayList<>();
        // Пример создания маршрутов с временными слотами
        for (int i = 1; i <= 10; i++) {
            Route route = new Route(i);
            // Добавляем временные слоты для маршрута
            // Предположим, что каждый маршрут имеет слоты с интервалом 1ч10мин
            LocalTime startTime = LocalTime.of(6, 0);
            int totalMinutes = 0;
            int operatingMinutes = 21 * 60; // 6:00 до 3:00 следующего дня = 21 час = 1260 минут

            while (totalMinutes < operatingMinutes) {
                LocalTime endTime = startTime.plusMinutes(70);
                boolean isPeak = isPeakHour(startTime);
                route.getTimeSlots().add(new TimeSlot(startTime, endTime, isPeak));
                startTime = endTime;

                // Если время выходит за пределы 23:59, переходим к началу суток
                if (startTime.isAfter(LocalTime.of(23, 59))) {
                    startTime = startTime.minusHours(24);
                }

                totalMinutes += 70;
            }
            routes.add(route);
        }
        return routes;
    }

    /**
     * Определяет, попадает ли заданное время в пиковый час.
     * Пиковые часы: 07:00–09:00 и 17:00–19:00.
     *
     * @param time Время, которое проверяется на пиковость.
     * @return {@code true}, если время попадает в пиковый час, {@code false} в противном случае.
     */
    private static boolean isPeakHour(LocalTime time) {
        // Определение, является ли время пиковым
        List<LocalTime[]> peakPeriods = Arrays.asList(
                new LocalTime[]{LocalTime.of(7, 0), LocalTime.of(9, 0)},
                new LocalTime[]{LocalTime.of(17, 0), LocalTime.of(19, 0)}
        );
        for (LocalTime[] period : peakPeriods) {
            if (!time.isBefore(period[0]) && time.isBefore(period[1])) {
                return true;
            }
        }
        return false;
    }

    /**
     * Создаёт копию списка автобусов.
     *
     * @param buses Список автобусов для копирования.
     * @return Копия списка автобусов.
     */
    private static List<Bus> cloneBuses(List<Bus> buses) {
        return buses.stream().map(bus -> new Bus(bus.getId())).collect(Collectors.toList());
    }

    /**
     * Создаёт копию списка водителей.
     *
     * @param drivers Список водителей для копирования.
     * @return Копия списка водителей.
     */
    private static List<Driver> cloneDrivers(List<Driver> drivers) {
        return drivers.stream().map(driver -> new Driver(driver.getId(), driver.getType())).collect(Collectors.toList());
    }

    /**
     * Создаёт копию списка маршрутов.
     *
     * @param routes Список маршрутов для копирования.
     * @return Копия списка маршрутов.
     */
    private static List<Route> cloneRoutes(List<Route> routes) {
        List<Route> clonedRoutes = new ArrayList<>();
        for (Route route : routes) {
            Route clonedRoute = new Route(route.getId());
            for (TimeSlot slot : route.getTimeSlots()) {
                clonedRoute.getTimeSlots().add(new TimeSlot(slot.getStartTime(), slot.getEndTime(), slot.isPeak()));
            }
            clonedRoutes.add(clonedRoute);
        }
        return clonedRoutes;
    }

    /**
     * Подсчитывает количество конфликтов в расписании, т.е. количество пересечений назначений водителей или автобусов.
     *
     * @param schedule Расписание, в котором подсчитываются конфликты.
     * @return Количество конфликтов в расписании.
     */
    private static int calculateConflicts(Schedule schedule) {
        int conflicts = 0;
        List<Assignment> assignments = schedule.getAssignments();
        for (int i = 0; i < assignments.size(); i++) {
            for (int j = i + 1; j < assignments.size(); j++) {
                Assignment a = assignments.get(i);
                Assignment b = assignments.get(j);
                // Проверка конфликтов по водителям
                if (a.getDriver().getId() == b.getDriver().getId()) {
                    if (!(a.getTimeSlot().getEndTime().isBefore(b.getTimeSlot().getStartTime()) ||
                            a.getTimeSlot().getStartTime().isAfter(b.getTimeSlot().getEndTime()))) {
                        conflicts++;
                    }
                }
                // Проверка конфликтов по автобусам
                if (a.getBus().getId() == b.getBus().getId()) {
                    if (!(a.getTimeSlot().getEndTime().isBefore(b.getTimeSlot().getStartTime()) ||
                            a.getTimeSlot().getStartTime().isAfter(b.getTimeSlot().getEndTime()))) {
                        conflicts++;
                    }
                }
            }
        }
        return conflicts;
    }

    /**
     * Выводит детализированные назначения для заданного алгоритма.
     *
     * @param algorithmName Название алгоритма (например, "Жадного алгоритма").
     * @param schedule      Расписание, для которого выводятся назначения.
     */
    private static void printAssignments(String algorithmName, Schedule schedule) {
        System.out.println("\nДетализированные назначения " + algorithmName + ":");
        for (Assignment a : schedule.getAssignments()) {
            System.out.println("Маршрут " + a.getRoute().getId() +
                    " назначен автобусу " + a.getBus().getId() +
                    ", водителю " + a.getDriver().getId() +
                    " с " + a.getTimeSlot().getStartTime() +
                    " до " + a.getTimeSlot().getEndTime() +
                    " (Пиковый: " + (a.getTimeSlot().isPeak() ? "Да" : "Нет") + ")");
        }
    }
}
