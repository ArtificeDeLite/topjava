package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.UserTestData.getNew;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends UserServiceTest {

    @Test
    public void getWithMeal() throws Exception {
        List<Meal> meals = List.of(ADMIN_MEAL2, ADMIN_MEAL1);
        User user = service.getWithMeal(ADMIN_ID);
        MEAL_MATCHER.assertMatch(user.getMeals(), meals);
    }

    @Test(expected = NotFoundException.class)
    public void getWithMealNotFound() throws Exception {
        User user = service.getWithMeal(1);
    }

    @Test
    public void getWithNoMeal() throws Exception {
        User created = service.create(getNew());
        User user = service.getWithMeal(created.getId());
        MEAL_MATCHER.assertMatch(user.getMeals(), List.of());
    }
}
