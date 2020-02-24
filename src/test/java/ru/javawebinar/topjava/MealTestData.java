package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final int MEAL_01_ID = START_SEQ + 2;
    public static final int MEAL_02_ID = START_SEQ + 3;
    public static final int MEAL_03_ID = START_SEQ + 4;
    public static final int MEAL_04_ID = START_SEQ + 5;
    public static final int MEAL_05_ID = START_SEQ + 6;
    public static final int MEAL_06_ID = START_SEQ + 7;
    public static final int MEAL_07_ID = START_SEQ + 8;
    public static final int MEAL_08_ID = START_SEQ + 9;
    public static final int MEAL_09_ID = START_SEQ + 10;
    public static final int MEAL_10_ID = START_SEQ + 11;

    public static final Meal MEAL_01 = new Meal(MEAL_01_ID, LocalDateTime.of(2020,
            Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Meal MEAL_02 = new Meal(MEAL_02_ID, LocalDateTime.of(2020,
            Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Meal MEAL_03 = new Meal(MEAL_03_ID, LocalDateTime.of(2020,
            Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final Meal MEAL_04 = new Meal(MEAL_04_ID, LocalDateTime.of(2020,
            Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Meal MEAL_05 = new Meal(MEAL_05_ID, LocalDateTime.of(2020,
            Month.JANUARY, 31, 10, 0), "Завтрак", 1000);
    public static final Meal MEAL_06 = new Meal(MEAL_06_ID, LocalDateTime.of(2020,
            Month.JANUARY, 31, 13, 0), "Обед", 500);
    public static final Meal MEAL_07 = new Meal(MEAL_07_ID, LocalDateTime.of(2020,
            Month.JANUARY, 31, 20, 0), "Ужин", 410);
    public static final Meal MEAL_08 = new Meal(MEAL_08_ID, LocalDateTime.of(2020,
            Month.FEBRUARY, 1, 10, 0), "Завтрак", 500);
    public static final Meal MEAL_09 = new Meal(MEAL_09_ID, LocalDateTime.of(2020,
            Month.FEBRUARY, 1, 14, 0), "Обед", 1000);
    public static final Meal MEAL_10 = new Meal(MEAL_10_ID, LocalDateTime.of(2020,
            Month.FEBRUARY, 1, 20, 0), "Ужин", 1000);

    public static final User USER = new User(USER_ID, "User", "user@yandex.ru", "password", Role.ROLE_USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ROLE_ADMIN);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.now(), "New meal", 1000);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(MEAL_01.getId(), MEAL_01.getDateTime(), MEAL_01.getDescription(), MEAL_01.getCalories());
        updated.setDescription("UpdatedDescription");
        updated.setCalories(500);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields().isEqualTo(expected);
    }
}
