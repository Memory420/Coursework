package com.memory.Service;

public class Simulation {
    static final int TIME_SLOTS_PER_DAY = 126;
    static int finishedRoutes;

    public static void main(String[] args) {
        TimeInterval timeInterval = new TimeInterval(630);
        System.out.println(timeInterval.prettyPrint());
    }

}
class TimeInterval{
    private final int startTime;
    private final int endTime;

    public TimeInterval(int startTime) {
        if (startTime % 10 != 0) {
            throw new IllegalArgumentException("startTime должен быть кратен 10");
        }
        this.startTime = startTime;
        endTime = startTime + 10;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }
    public String prettyPrint() {
        String start = formatTime(startTime);
        String end = formatTime(endTime);
        return start + " - " + end;
    }

    private String formatTime(int minutes) {
        int hours = minutes / 60;
        int mins = minutes % 60;
        return String.format("%02d:%02d", hours, mins);
    }
}