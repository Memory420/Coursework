package com.memory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Реализует генетический алгоритм для создания оптимального расписания автобусов и водителей на маршруты.
 * Генетический алгоритм использует популяцию расписаний и операции селекции, кроссовера и мутации для поиска
 * наиболее эффективного решения.
 */
public class GeneticSchedularGA {
    private List<Bus> buses;
    private List<Driver> drivers;
    private List<Route> routes;
    private int populationSize;
    private int generations;
    private double mutationRate;
    private Random random;

    /**
     * Создаёт новый экземпляр генетического алгоритма планирования с указанными параметрами.
     *
     * @param buses          Список доступных автобусов.
     * @param drivers        Список доступных водителей.
     * @param routes         Список маршрутов, для которых необходимо создать расписание.
     * @param populationSize Размер популяции расписаний.
     * @param generations    Количество поколений для эволюции популяции.
     * @param mutationRate   Вероятность мутации для каждого назначения.
     */
    public GeneticSchedularGA(List<Bus> buses, List<Driver> drivers, List<Route> routes,
                              int populationSize, int generations, double mutationRate) {
        this.buses = buses;
        this.drivers = drivers;
        this.routes = routes;
        this.populationSize = populationSize;
        this.generations = generations;
        this.mutationRate = mutationRate;
        this.random = new Random();
    }

    /**
     * Создаёт оптимальное расписание, используя генетический алгоритм.
     *
     * @return Оптимальное расписание с назначенными маршрутами.
     */
    public Schedule createSchedule() {
        // Инициализация популяции
        List<Schedule> population = initializePopulation();

        // Эволюция популяции
        for (int i = 0; i < generations; i++) {
            List<Schedule> newPopulation = new ArrayList<>();

            // Селекция и кроссовер
            for (int j = 0; j < populationSize; j++) {
                Schedule parent1 = selectParent(population);
                Schedule parent2 = selectParent(population);
                Schedule child = crossover(parent1, parent2);
                newPopulation.add(child);
            }

            // Мутация
            for (Schedule schedule : newPopulation) {
                mutate(schedule);
            }

            population = newPopulation;
        }

        // Выбор лучшего расписания
        return selectBestSchedule(population);
    }

    /**
     * Инициализирует начальную популяцию расписаний, используя жадный алгоритм для создания разнообразных решений.
     *
     * @return Начальная популяция расписаний.
     */
    private List<Schedule> initializePopulation() {
        List<Schedule> population = new ArrayList<>();
        GreedyScheduler greedy = new GreedyScheduler(cloneBuses(buses), cloneDrivers(drivers), cloneRoutes(routes));
        for (int i = 0; i < populationSize; i++) {
            Schedule schedule = greedy.createSchedule();
            population.add(schedule);
        }
        return population;
    }

    /**
     * Выбирает родительское расписание из популяции с помощью турнирной селекции.
     *
     * @param population Текущая популяция расписаний.
     * @return Выбранное родительское расписание.
     */
    private Schedule selectParent(List<Schedule> population) {
        // Турнирная селекция
        int tournamentSize = 5;
        List<Schedule> tournament = new ArrayList<>();
        for (int i = 0; i < tournamentSize; i++) {
            Schedule randomSchedule = population.get(random.nextInt(population.size()));
            tournament.add(randomSchedule);
        }
        return tournament.stream().max(Comparator.comparing(this::fitness)).orElse(null);
    }

    /**
     * Выполняет одноточечный кроссовер между двумя родительскими расписаниями, создавая новое расписание-потомок.
     *
     * @param parent1 Первое родительское расписание.
     * @param parent2 Второе родительское расписание.
     * @return Созданное расписание-потомок.
     */
    private Schedule crossover(Schedule parent1, Schedule parent2) {
        // Одноточечный кроссовер
        Schedule child = new Schedule();
        int crossoverPoint = random.nextInt(parent1.getAssignments().size());
        for (int i = 0; i < parent1.getAssignments().size(); i++) {
            if (i < crossoverPoint) {
                child.addAssignment(parent1.getAssignments().get(i));
            } else {
                child.addAssignment(parent2.getAssignments().get(i));
            }
        }
        return child;
    }

    /**
     * Вносит случайные изменения в расписание, изменяя водителей или автобусы с заданной вероятностью мутации.
     *
     * @param schedule Расписание, в которое вносятся изменения.
     */
    private void mutate(Schedule schedule) {
        // Мутация: случайное изменение некоторых назначений
        for (int i = 0; i < schedule.getAssignments().size(); i++) {
            if (random.nextDouble() < mutationRate) {
                Assignment assignment = schedule.getAssignments().get(i);
                // Случайное переназначение водителя или автобуса
                if (random.nextBoolean()) {
                    // Переназначение водителя
                    Driver newDriver = drivers.get(random.nextInt(drivers.size()));
                    schedule.getAssignments().set(i, new Assignment(newDriver, assignment.getBus(),
                            assignment.getRoute(), assignment.getTimeSlot()));
                } else {
                    // Переназначение автобуса
                    Bus newBus = buses.get(random.nextInt(buses.size()));
                    schedule.getAssignments().set(i, new Assignment(assignment.getDriver(), newBus,
                            assignment.getRoute(), assignment.getTimeSlot()));
                }
            }
        }
    }

    /**
     * Оценивает пригодность расписания, определяя количество назначений без конфликтов.
     *
     * @param schedule Расписание, которое оценивается.
     * @return Оценка пригодности расписания.
     */
    private double fitness(Schedule schedule) {
        // Оценка пригодности расписания: количество назначений без конфликтов
        int score = 0;
        for (Assignment a : schedule.getAssignments()) {
            // Проверка наличия конфликтов
            boolean conflict = false;
            for (Assignment b : schedule.getAssignments()) {
                if (a == b) continue;
                if (a.getDriver().getId() == b.getDriver().getId()) {
                    if (!(a.getTimeSlot().getEndTime().isBefore(b.getTimeSlot().getStartTime()) ||
                            a.getTimeSlot().getStartTime().isAfter(b.getTimeSlot().getEndTime()))) {
                        conflict = true;
                        break;
                    }
                }
            }
            if (!conflict) {
                score++;
            }
        }
        return score;
    }

    /**
     * Выбирает лучшее расписание из популяции на основе оценки пригодности.
     *
     * @param population Популяция расписаний.
     * @return Лучшее расписание из популяции.
     */
    private Schedule selectBestSchedule(List<Schedule> population) {
        return population.stream().max(Comparator.comparing(this::fitness)).orElse(null);
    }

    /**
     * Создаёт копию списка автобусов.
     *
     * @param buses Список автобусов для копирования.
     * @return Копия списка автобусов.
     */
    private List<Bus> cloneBuses(List<Bus> buses) {
        return buses.stream().map(bus -> new Bus(bus.getId())).collect(Collectors.toList());
    }

    /**
     * Создаёт копию списка водителей.
     *
     * @param drivers Список водителей для копирования.
     * @return Копия списка водителей.
     */
    private List<Driver> cloneDrivers(List<Driver> drivers) {
        return drivers.stream().map(driver -> new Driver(driver.getId(), driver.getType())).collect(Collectors.toList());
    }

    /**
     * Создаёт копию списка маршрутов.
     *
     * @param routes Список маршрутов для копирования.
     * @return Копия списка маршрутов.
     */
    private List<Route> cloneRoutes(List<Route> routes) {
        List<Route> clonedRoutes = new ArrayList<>();
        for (Route route : routes) {
            Route clonedRoute = new Route(route.getId());
            for (TimeSlot slot : route.getTimeSlots()) {
                clonedRoute.getTimeSlots().add(new TimeSlot(slot.getStartTime(), slot.getEndTime(), slot.isPeak()));
            }
            clonedRoutes.add(clonedRoute);
        }
        return clonedRoutes;
    }
}
