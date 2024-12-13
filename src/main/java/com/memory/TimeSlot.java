package com.memory;

import java.time.LocalTime;

/**
 * Представляет временной слот с начальным и конечным временем, а также флагом, указывающим
 * является ли этот период пиковым часом.
 */
public class TimeSlot {
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean isPeak;

    /**
     * Создаёт новый временной слот с указанным начальным и конечным временем и флагом пиковости.
     *
     * @param startTime Время начала временного слота.
     * @param endTime   Время окончания временного слота.
     * @param isPeak    {@code true}, если временной слот попадает в пиковый час, {@code false} в противном случае.
     */
    public TimeSlot(LocalTime startTime, LocalTime endTime, boolean isPeak) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.isPeak = isPeak;
    }

    /**
     * Возвращает время начала временного слота.
     *
     * @return Время начала.
     */
    public LocalTime getStartTime() {
        return startTime;
    }

    /**
     * Возвращает время окончания временного слота.
     *
     * @return Время окончания.
     */
    public LocalTime getEndTime() {
        return endTime;
    }

    /**
     * Проверяет, является ли временной слот пиковым часом.
     *
     * @return {@code true}, если слот пиковый, {@code false} в противном случае.
     */
    public boolean isPeak() {
        return isPeak;
    }

    /**
     * Возвращает строковое представление временного слота, включающее время начала и окончания
     * и информацию о пиковости.
     *
     * @return Строковое представление временного слота.
     */
    @Override
    public String toString() {
        return "TimeSlot{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", isPeak=" + isPeak +
                '}';
    }
}
