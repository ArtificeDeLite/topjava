package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles("datajpa")
public class DataJpaUserServiceTest extends UserServiceTest {

    @Test
    public void getWithMeal() throws Exception {
        List<Meal> meals = List.of(ADMIN_MEAL1, ADMIN_MEAL2);
        User user = service.getWithMeal(ADMIN_ID);
        MEAL_MATCHER.assertMatch(user.getMeals(), meals);
    }
}
