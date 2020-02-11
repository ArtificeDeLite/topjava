package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.dao.MealDaoMap;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class MealServlet extends HttpServlet {

    private static String INSERT_OR_EDIT = "/meal.jsp";
    private static String LIST_MEALS = "/meals.jsp";
    private MealDaoMap dao;
    private final int CALORIES_PER_DAY = 2000;

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    public void init() throws ServletException {
        super.init();
        dao = new MealDaoMap();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward;
        String action = request.getParameter("action");
        if (action == null)
            action = "";

        switch (action) {
            case ("delete"): {
                int id = Integer.parseInt(request.getParameter("mealId"));
                dao.delete(id);
                response.sendRedirect(request.getContextPath() + "/meals");
                return;
            }
            case ("insert"): {
                forward = INSERT_OR_EDIT;
                break;
            }
            case ("edit"): {
                forward = INSERT_OR_EDIT;
                int id = Integer.parseInt(request.getParameter("mealId"));
                Meal meal = dao.get(id);
                request.setAttribute("meal", meal);
                break;
            }
            default: {
                forward = LIST_MEALS;
                request.setAttribute("meals", MealsUtil.filteredByStreams(dao.getAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
            }
        }
        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime").replace("T", " "), dateTimeFormatter);

        String stringId = request.getParameter("id");
        if (stringId == null || stringId.isEmpty()) {
            dao.add(new Meal(0, dateTime, description, calories));
        } else {
            int id = Integer.parseInt(request.getParameter("id"));
            dao.update(new Meal(id, dateTime, description, calories));
        }
        response.sendRedirect(request.getContextPath() + "/meals");
    }
}
