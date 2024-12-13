package com.memory;

/**
 * Представляет автобус с уникальным идентификатором и статусом доступности.
 * Содержит методы для назначения и освобождения автобуса.
 */
public class Bus {
    private int id;
    private boolean isAvailable;

    /**
     * Создаёт новый автобус с указанным идентификатором и устанавливает его доступным.
     *
     * @param id Уникальный идентификатор автобуса.
     */
    public Bus(int id) {
        this.id = id;
        this.isAvailable = true;
    }

    /**
     * Возвращает уникальный идентификатор автобуса.
     *
     * @return Идентификатор автобуса.
     */
    public int getId() {
        return id;
    }

    /**
     * Проверяет, доступен ли автобус для назначения.
     *
     * @return {@code true}, если автобус доступен, {@code false} в противном случае.
     */
    public boolean isAvailable() {
        return isAvailable;
    }

    /**
     * Назначает автобус, делая его недоступным для других маршрутов до освобождения.
     */
    public void assign() {
        this.isAvailable = false;
    }

    /**
     * Освобождает автобус, делая его доступным для назначения на другие маршруты.
     */
    public void release() {
        this.isAvailable = true;
    }

    /**
     * Возвращает строковое представление автобуса, включающее его идентификатор.
     *
     * @return Строковое представление автобуса.
     */
    @Override
    public String toString() {
        return "Bus{" +
                "id=" + id +
                '}';
    }
}
