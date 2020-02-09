package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");

        // Данные
        List<MealTo> meals = Arrays.asList(
                new MealTo(1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500, false),
                new MealTo(2, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000, false),
                new MealTo(3, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500, false),
                new MealTo(4, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100, true),
                new MealTo(5, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000, true),
                new MealTo(6, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500, true),
                new MealTo(7, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410, true)
        );

        request.setAttribute("meals", meals);

        request.getRequestDispatcher("/meals_simple.jsp").forward(request, response);
//        response.sendRedirect("meals.jsp");
    }
}
