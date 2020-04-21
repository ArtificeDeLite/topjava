package ru.javawebinar.topjava.service;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.DataAlreadyExistException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.atStartOfDayOrMin;
import static ru.javawebinar.topjava.util.DateTimeUtil.atStartOfNextDayOrMax;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal get(int id, int userId) {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public List<Meal> getBetweenInclusive(@Nullable LocalDate startDate, @Nullable LocalDate endDate, int userId) {
        return repository.getBetweenHalfOpen(atStartOfDayOrMin(startDate), atStartOfNextDayOrMax(endDate), userId);
    }

    public List<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }

    @Transactional
    public void update(Meal meal, int userId) {
        Assert.notNull(meal, "meal must not be null");
        Meal checkMeal = getByDateTime(meal.getDateTime(), userId);
        if (checkMeal != null && !meal.getId().equals(checkMeal.getId())) {
            throw new DataAlreadyExistException("Meal.AlreadyExists");
        }
        checkNotFoundWithId(repository.save(meal, userId), meal.id());
    }

    @Transactional
    public Meal create(Meal meal, int userId) {
        Assert.notNull(meal, "meal must not be null");
        if (getByDateTime(meal.getDateTime(), userId) != null) {
            throw new DataAlreadyExistException("Meal.AlreadyExists");
        }
        return repository.save(meal, userId);
    }

    public Meal getWithUser(int id, int userId) {
        return checkNotFoundWithId(repository.getWithUser(id, userId), id);
    }

    public Meal getByDateTime(LocalDateTime dateTime, int userId) {
        if (dateTime == null) return null;
        List<Meal> meals = repository.getBetweenHalfOpen(dateTime, dateTime.plusMinutes(1), userId);
        if (meals == null) return null;
        return DataAccessUtils.singleResult(meals);
    }
}