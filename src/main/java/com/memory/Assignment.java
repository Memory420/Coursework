package com.memory;

/**
 * Представляет назначение водителя и автобуса на конкретный маршрут в определённый временной слот.
 * Содержит информацию о назначенном водителе, автобусе, маршруте и временном слоте.
 */
public class Assignment {
    private Driver driver;
    private Bus bus;
    private Route route;
    private TimeSlot timeSlot;

    /**
     * Создаёт новое назначение с указанными водителем, автобусом, маршрутом и временным слотом.
     *
     * @param driver   Водитель, назначенный на маршрут.
     * @param bus      Автобус, назначенный на маршрут.
     * @param route    Маршрут, на который назначены водитель и автобус.
     * @param timeSlot Временной слот, в который назначено выполнение маршрута.
     */
    public Assignment(Driver driver, Bus bus, Route route, TimeSlot timeSlot) {
        this.driver = driver;
        this.bus = bus;
        this.route = route;
        this.timeSlot = timeSlot;
    }

    /**
     * Возвращает водителя, назначенного на маршрут.
     *
     * @return Назначенный водитель.
     */
    public Driver getDriver() {
        return driver;
    }

    /**
     * Возвращает автобус, назначенный на маршрут.
     *
     * @return Назначенный автобус.
     */
    public Bus getBus() {
        return bus;
    }

    /**
     * Возвращает маршрут, на который назначены водитель и автобус.
     *
     * @return Назначенный маршрут.
     */
    public Route getRoute() {
        return route;
    }

    /**
     * Возвращает временной слот, в который назначено выполнение маршрута.
     *
     * @return Временной слот назначения.
     */
    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    /**
     * Возвращает строковое представление назначения, включающее информацию о водителе, автобусе, маршруте и временном слоте.
     *
     * @return Строковое представление назначения.
     */
    @Override
    public String toString() {
        return "Assignment{" +
                "driver=" + driver +
                ", bus=" + bus +
                ", route=" + route +
                ", timeSlot=" + timeSlot +
                '}';
    }
}
