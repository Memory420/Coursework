package com.memory;

import java.util.ArrayList;
import java.util.List;

/**
 * Хранит список всех назначений водителей и автобусов на маршруты.
 * Обеспечивает проверку наличия конфликтов при добавлении новых назначений.
 */
public class Schedule {
    private List<Assignment> assignments;

    /**
     * Создаёт новое расписание с пустым списком назначений.
     */
    public Schedule() {
        this.assignments = new ArrayList<>();
    }

    /**
     * Возвращает список всех назначений в расписании.
     *
     * @return Список назначений.
     */
    public List<Assignment> getAssignments() {
        return assignments;
    }

    /**
     * Добавляет новое назначение в расписание.
     *
     * @param assignment Назначение, которое необходимо добавить.
     */
    public void addAssignment(Assignment assignment) {
        assignments.add(assignment);
    }

    /**
     * Проверяет наличие конфликта для водителя в указанном временном слоте.
     * Конфликт возникает, если водитель уже назначен на другой маршрут в пересекающемся временном слоте.
     *
     * @param driver Водитель, для которого проверяется конфликт.
     * @param slot   Временной слот, в котором проверяется конфликт.
     * @return {@code true}, если есть конфликт, {@code false} в противном случае.
     */
    public boolean isConflict(Driver driver, TimeSlot slot) {
        for (Assignment a : assignments) {
            if (a.getDriver().getId() == driver.getId()) {
                // Проверка перекрытия временных слотов
                if (!(slot.getEndTime().isBefore(a.getTimeSlot().getStartTime()) ||
                        slot.getStartTime().isAfter(a.getTimeSlot().getEndTime()))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Возвращает строковое представление расписания, включающее все назначения.
     *
     * @return Строковое представление расписания.
     */
    @Override
    public String toString() {
        return "Schedule{" +
                "assignments=" + assignments +
                '}';
    }
}
