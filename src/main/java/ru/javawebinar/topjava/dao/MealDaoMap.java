package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MealDaoMap {
    private ConcurrentMap<Integer, Meal> concurentMeals;
    private volatile AtomicInteger counter = new AtomicInteger(0);

    public MealDaoMap() {
        concurentMeals = Stream.of(new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(2, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(3, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(4, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(5, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(6, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(7, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410))
                .collect(Collectors.toConcurrentMap(Meal::getId, Function.identity()));
        counter.set(7);
    }

    public void addMeal(Meal meal) {
        Meal newMeal = new Meal(counter.incrementAndGet(), meal.getDateTime(), meal.getDescription(), meal.getCalories());
        concurentMeals.put(newMeal.getId(), newMeal);
    }

    public void deleteMeal(int mealId) {
        concurentMeals.remove(mealId);
    }

    public void updateMeal(Meal meal) {
        concurentMeals.replace(meal.getId(), meal);
    }

    public List<MealTo> getAllMeals() {
        return MealsUtil.filteredByStreams(new ArrayList<>(concurentMeals.values()), LocalTime.MIN, LocalTime.MAX, 2000);
    }

    public Meal getMealById(int mealId) {
        return concurentMeals.get(mealId);
    }
}
