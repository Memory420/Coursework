package com.memory.Entity;

/**
 * Класс {@code TimeInterval} представляет собой временной интервал
 * с заданным временем начала и автоматически рассчитанным временем окончания.
 * Время задается в минутах с начала суток, и должно быть кратно 10.
 */
public class TimeInterval {

    private final int startTime; // Время начала интервала в минутах
    private final int endTime;    // Время окончания интервала в минутах
    // Здесь нужно добавить какое то поле которое будет указывать что будет происходить в этот промежуток времени
    // Возникает проблема с тем что должно быть в массиве TimeInterval в Simulation, потому что массив "времени" не один
    // на всю симуляцию - каждый автобус будет иметь такой таймлайн, наверное нужно как то итерироваться по всех Route

    /**
     * Конструктор для создания временного интервала с заданным временем начала.
     *
     * @param startTime Время начала интервала в минутах с начала суток (0 - 1439).
     *                  Значение должно быть кратно 10.
     * @throws IllegalArgumentException Если {@code startTime} не кратен 10.
     */
    public TimeInterval(int startTime) {
        if (startTime % 10 != 0) {
            throw new IllegalArgumentException("startTime должен быть кратен 10");
        }
        this.startTime = startTime;
        endTime = startTime + 10;
    }

    /**
     * Возвращает время начала интервала.
     *
     * @return Время начала интервала в минутах с начала суток.
     */
    public int getStartTime() {
        return startTime;
    }

    /**
     * Возвращает время окончания интервала.
     *
     * @return Время окончания интервала в минутах с начала суток.
     */
    public int getEndTime() {
        return endTime;
    }

    /**
     * Форматирует временной интервал в читаемую строку.
     *
     * @return Строка, представляющая временной интервал в формате "HH:mm - HH:mm".
     */
    public String prettyPrint() {
        String start = formatTime(startTime);
        String end = formatTime(endTime);
        return start + " - " + end;
    }

    /**
     * Форматирует время из минут в строку формата "HH:mm".
     *
     * @param minutes Время в минутах с начала суток.
     * @return Форматированная строка времени.
     */
    private String formatTime(int minutes) {
        int hours = minutes / 60;
        int mins = minutes % 60;
        return String.format("%02d:%02d", hours, mins);
    }
}
