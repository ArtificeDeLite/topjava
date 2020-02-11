package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;


public class MealDaoMap implements IDaoManager<Meal> {
    private ConcurrentMap<Integer, Meal> concurentMeals = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    public MealDaoMap() {
        add(new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        add(new Meal(2, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        add(new Meal(3, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        add(new Meal(4, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        add(new Meal(5, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        add(new Meal(6, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        add(new Meal(7, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    public void add(Meal meal) {
        Meal newMeal = new Meal(counter.incrementAndGet(), meal.getDateTime(), meal.getDescription(), meal.getCalories());
        concurentMeals.put(newMeal.getId(), newMeal);
    }

    public void delete(int mealId) {
        concurentMeals.remove(mealId);
    }

    public void update(Meal meal) {
        concurentMeals.replace(meal.getId(), meal);
    }

    public List<Meal> getAll() {
        return new ArrayList<>(concurentMeals.values());
    }

    public Meal get(int mealId) {
        return concurentMeals.get(mealId);
    }
}