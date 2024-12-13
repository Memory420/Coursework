package com.memory;

/**
 * Представляет водителя с уникальным идентификатором, типом и расписанием.
 * Водители могут быть двух типов: TYPE_A и TYPE_B, с разными правилами работы и отдыха.
 */
public class Driver {
    /**
     * Перечисление типов водителей.
     */
    public enum DriverType { TYPE_A, TYPE_B }

    private int id;
    private DriverType type;
    private Schedule schedule;

    /**
     * Создаёт нового водителя с указанным идентификатором и типом.
     *
     * @param id   Уникальный идентификатор водителя.
     * @param type Тип водителя (TYPE_A или TYPE_B).
     */
    public Driver(int id, DriverType type) {
        this.id = id;
        this.type = type;
        this.schedule = new Schedule();
    }

    /**
     * Возвращает уникальный идентификатор водителя.
     *
     * @return Идентификатор водителя.
     */
    public int getId() {
        return id;
    }

    /**
     * Возвращает тип водителя.
     *
     * @return Тип водителя.
     */
    public DriverType getType() {
        return type;
    }

    /**
     * Возвращает расписание водителя.
     *
     * @return Расписание водителя.
     */
    public Schedule getSchedule() {
        return schedule;
    }

    /**
     * Возвращает строковое представление водителя, включающее его идентификатор и тип.
     *
     * @return Строковое представление водителя.
     */
    @Override
    public String toString() {
        return "Driver{" +
                "id=" + id +
                ", type=" + type +
                '}';
    }
}
