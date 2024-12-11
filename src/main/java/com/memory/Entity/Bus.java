package com.memory.Entity;

/**
 * Класс {@code Bus} представляет автобус с уникальным идентификатором, водителем и состоянием на маршруте.
 */
public class Bus {
    private int id;           // Уникальный идентификатор автобуса
    private Driver driver;    // Водитель, назначенный на автобус
    private boolean onRoute;  // Состояние на маршруте

    /**
     * Возвращает состояние автобуса: находится ли он на маршруте.
     *
     * @return {@code true}, если автобус находится на маршруте; {@code false} в противном случае.
     */
    public boolean isOnRoute() {
        return onRoute;
    }

    /**
     * Конструктор для создания автобуса с заданным идентификатором.
     *
     * @param id Уникальный идентификатор автобуса.
     */
    public Bus(int id) {
        this.id = id;
    }

    public void setOnRoute(boolean onRoute) {
        this.onRoute = onRoute;
    }

    /**
     * Конструктор для создания автобуса с назначенным водителем.
     *
     * @param driver Водитель, который будет назначен на автобус.
     */
    public Bus(Driver driver) {
        this.driver = driver;
    }

    /**
     * Возвращает строковое представление автобуса.
     *
     * @return Строка, представляющая автобус, включая его идентификатор, название водителя и состояние на маршруте.
     */
    @Override
    public String toString() {
        if (driver != null) {
            this.onRoute = true;
            return String.format("Bus [id=%d, driver=%s, onRoute=%b]", id, this.driver.getName(), this.onRoute);
        } else {
            this.onRoute = false;
            return String.format("Bus [id=%d, driver=не назначен, onRoute=%b]", id, false);
        }
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
}
