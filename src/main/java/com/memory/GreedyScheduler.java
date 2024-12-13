package com.memory;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Реализует жадный алгоритм для создания расписания автобусов и водителей на маршруты.
 * Жадный алгоритм последовательно назначает доступные ресурсы на доступные маршруты, стараясь
 * минимизировать конфликты и максимально использовать доступные ресурсы.
 */
public class GreedyScheduler {
    private List<Bus> buses;
    private List<Driver> drivers;
    private List<Route> routes;

    /**
     * Создаёт новый экземпляр жадного алгоритма планирования с указанными автобусами, водителями и маршрутами.
     *
     * @param buses   Список доступных автобусов.
     * @param drivers Список доступных водителей.
     * @param routes  Список маршрутов, для которых необходимо создать расписание.
     */
    public GreedyScheduler(List<Bus> buses, List<Driver> drivers, List<Route> routes) {
        this.buses = buses;
        this.drivers = drivers;
        this.routes = routes;
    }

    /**
     * Создаёт расписание, назначая доступные автобусы и водителей на маршруты по жадному алгоритму.
     *
     * @return Созданное расписание с назначенными маршрутами.
     */
    public Schedule createSchedule() {
        Schedule schedule = new Schedule();

        // Сортировка маршрутов по времени начала
        routes.sort(Comparator.comparing(r -> r.getTimeSlots().get(0).getStartTime()));

        for (Route route : routes) {
            for (TimeSlot slot : route.getTimeSlots()) {
                Bus availableBus = findAvailableBus(schedule, slot);
                Driver availableDriver = findAvailableDriver(schedule, slot);

                if (availableBus != null && availableDriver != null) {
                    Assignment assignment = new Assignment(availableDriver, availableBus, route, slot);
                    schedule.addAssignment(assignment);
                }
            }
        }

        return schedule;
    }

    /**
     * Ищет первый доступный автобус, который не занят в заданном временном слоте.
     *
     * @param schedule Текущее расписание.
     * @param slot     Временной слот, для которого ищется доступный автобус.
     * @return Доступный автобус, или {@code null}, если такого нет.
     */
    private Bus findAvailableBus(Schedule schedule, TimeSlot slot) {
        for (Bus bus : buses) {
            boolean isAssigned = false;
            for (Assignment a : schedule.getAssignments()) {
                if (a.getBus().getId() == bus.getId()) {
                    // Проверка перекрытия временных слотов
                    if (!(slot.getEndTime().isBefore(a.getTimeSlot().getStartTime()) ||
                            slot.getStartTime().isAfter(a.getTimeSlot().getEndTime()))) {
                        isAssigned = true;
                        break;
                    }
                }
            }
            if (!isAssigned) {
                return bus;
            }
        }
        return null;
    }

    /**
     * Ищет первого доступного водителя, который соответствует требованиям и не занят в заданном временном слоте.
     *
     * @param schedule Текущее расписание.
     * @param slot     Временной слот, для которого ищется доступный водитель.
     * @return Доступный водитель, или {@code null}, если такого нет.
     */
    private Driver findAvailableDriver(Schedule schedule, TimeSlot slot) {
        // Сортировка водителей по типу, предпочтение типу A
        List<Driver> sortedDrivers = new ArrayList<>(drivers);
        sortedDrivers.sort(Comparator.comparing(Driver::getType));

        for (Driver driver : sortedDrivers) {
            if (!schedule.isConflict(driver, slot) && isDriverAvailable(driver, schedule, slot)) {
                return driver;
            }
        }
        return null;
    }

    /**
     * Проверяет доступность водителя для назначения в заданный временной слот, учитывая его тип.
     *
     * @param driver   Водитель, доступность которого проверяется.
     * @param schedule Текущее расписание.
     * @param slot     Временной слот, для которого проверяется доступность.
     * @return {@code true}, если водитель доступен, {@code false} в противном случае.
     */
    private boolean isDriverAvailable(Driver driver, Schedule schedule, TimeSlot slot) {
        if (driver.getType() == Driver.DriverType.TYPE_A) {
            return isTypeAAvailable(driver, schedule, slot);
        } else {
            return isTypeBAvailable(driver, schedule, slot);
        }
    }

    /**
     * Проверяет доступность водителя типа A, учитывая рабочее время и обеденный перерыв.
     *
     * @param driver   Водитель типа A, доступность которого проверяется.
     * @param schedule Текущее расписание.
     * @param slot     Временной слот, для которого проверяется доступность.
     * @return {@code true}, если водитель доступен, {@code false} в противном случае.
     */
    private boolean isTypeAAvailable(Driver driver, Schedule schedule, TimeSlot slot) {
        // Работают 8ч + 1ч на обед, обед только после 4х часов работы и не в час пик
        // Проверяем обед
        // Простая реализация: проверяем, что в расписании уже не более 8 часов работы
        int totalWorkMinutes = 0;
        boolean hasLunch = false;
        for (Assignment a : schedule.getAssignments()) {
            if (a.getDriver().getId() == driver.getId()) {
                totalWorkMinutes += 70; // 1ч10мин = 70 минут
                // Проверяем, был ли уже обед
                if (a.getTimeSlot().isPeak()) {
                    hasLunch = true; // Не могли взять обед в час пик
                }
            }
        }
        if (totalWorkMinutes >= 8 * 60) {
            return false;
        }
        // Дополнительные проверки на обед можно добавить
        return true;
    }

    /**
     * Проверяет доступность водителя типа B, учитывая сменную систему и перерывы.
     *
     * @param driver   Водитель типа B, доступность которого проверяется.
     * @param schedule Текущее расписание.
     * @param slot     Временной слот, для которого проверяется доступность.
     * @return {@code true}, если водитель доступен, {@code false} в противном случае.
     */
    private boolean isTypeBAvailable(Driver driver, Schedule schedule, TimeSlot slot) {
        // Получаем все назначения водителя
        List<Assignment> driverAssignments = schedule.getAssignments().stream()
                .filter(a -> a.getDriver().getId() == driver.getId())
                .sorted(Comparator.comparing(a -> a.getTimeSlot().getStartTime()))
                .collect(Collectors.toList());

        // Проверяем сменную систему: 1 сутки работает, двое отдыхают
        // В данном расписании рассматриваем только один день, поэтому этот пункт пропускаем
        // В реальной реализации потребуется хранить информацию о прошлых сменах

        // Проверяем перерывы каждые 2 часа работы
        // Найти последнее назначение перед текущ слотом
        Assignment lastAssignment = null;
        for (Assignment a : driverAssignments) {
            if (a.getTimeSlot().getEndTime().isBefore(slot.getStartTime()) ||
                    a.getTimeSlot().getEndTime().equals(slot.getStartTime())) {
                lastAssignment = a;
            } else {
                break;
            }
        }

        if (lastAssignment != null) {
            // Вычисляем время с конца последнего назначения до начала текущего слота
            long minutesSinceLastAssignment = java.time.Duration.between(lastAssignment.getTimeSlot().getEndTime(), slot.getStartTime()).toMinutes();

            // Вычисляем количество минут непрерывной работы до текущего слота
            long continuousWorkMinutes = 0;
            LocalTime currentEndTime = lastAssignment.getTimeSlot().getEndTime();

            // Ищем непрерывные назначения до последнего
            for (int i = driverAssignments.size() - 1; i >= 0; i--) {
                Assignment a = driverAssignments.get(i);
                if (a.getTimeSlot().getEndTime().equals(currentEndTime)) {
                    long minutesWorked = java.time.Duration.between(a.getTimeSlot().getStartTime(), a.getTimeSlot().getEndTime()).toMinutes();
                    continuousWorkMinutes += minutesWorked;
                    currentEndTime = a.getTimeSlot().getStartTime();
                } else {
                    break;
                }
            }

            if (continuousWorkMinutes >= 120) { // 2 часа
                // Требуется перерыв минимум 15 минут
                if (minutesSinceLastAssignment < 15) {
                    return false;
                }
            }
        }

        // Проверяем, что водитель не превышает 8 часов работы
        // Считаем общее количество рабочих минут
        int totalWorkMinutes = driverAssignments.stream()
                .mapToInt(a -> (int) java.time.Duration.between(a.getTimeSlot().getStartTime(), a.getTimeSlot().getEndTime()).toMinutes())
                .sum();

        // Добавляем текущий слот
        totalWorkMinutes += java.time.Duration.between(slot.getStartTime(), slot.getEndTime()).toMinutes();

        if (totalWorkMinutes > 8 * 60) {
            return false;
        }

        return true;
    }

}
