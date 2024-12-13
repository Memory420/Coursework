package com.memory;

import java.util.ArrayList;
import java.util.List;

/**
 * Представляет маршрут с уникальным идентификатором и списком временных слотов.
 * Временные слоты определяют, когда маршрут должен быть обслужен.
 */
public class Route {
    private int id;
    private List<TimeSlot> timeSlots;

    /**
     * Создаёт новый маршрут с указанным идентификатором.
     *
     * @param id Уникальный идентификатор маршрута.
     */
    public Route(int id) {
        this.id = id;
        this.timeSlots = new ArrayList<>();
    }

    /**
     * Возвращает уникальный идентификатор маршрута.
     *
     * @return Идентификатор маршрута.
     */
    public int getId() {
        return id;
    }

    /**
     * Возвращает список временных слотов, в которые должен быть обслужен маршрут.
     *
     * @return Список временных слотов маршрута.
     */
    public List<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    /**
     * Возвращает строковое представление маршрута, включающее его идентификатор.
     *
     * @return Строковое представление маршрута.
     */
    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                '}';
    }
}
