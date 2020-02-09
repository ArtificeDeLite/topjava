package ru.javawebinar.topjava.controller;

import ru.javawebinar.topjava.dao.MealDaoMap;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MealController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static String INSERT_OR_EDIT = "/meal.jsp";
    private static String LIST_MEALS = "/meals.jsp";
    private MealDaoMap dao;

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public MealController() {
        super();
        dao = new MealDaoMap();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward;
        String action = request.getParameter("action");

        if (action == null || action.isEmpty()) {
            forward = LIST_MEALS;
            request.setAttribute("meals", dao.getAllMeals());
        } else if (action.equalsIgnoreCase("delete")) {
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            dao.deleteMeal(mealId);
            response.sendRedirect(request.getContextPath() + "/meals");
            return;
        } else if (action.equalsIgnoreCase("edit")) {
            forward = INSERT_OR_EDIT;
            int id = Integer.parseInt(request.getParameter("mealId"));
            Meal meal = dao.getMealById(id);
            request.setAttribute("meal", meal);
        } else if (action.equalsIgnoreCase("listMeal")) {
            forward = LIST_MEALS;
            request.setAttribute("meals", dao.getAllMeals());
        } else {
            forward = INSERT_OR_EDIT;
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        LocalDateTime dateTime = null;
        try {
            dateTime = LocalDateTime.parse(request.getParameter("dateTime").replace("T", " "), dateTimeFormatter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String stringId = request.getParameter("id");
        if (stringId == null || stringId.isEmpty()) {
            dao.addMeal(new Meal(0, dateTime, description, calories));
        } else {
            int id = Integer.parseInt(request.getParameter("id"));
            dao.updateMeal(new Meal(id, dateTime, description, calories));
        }
        RequestDispatcher view = request.getRequestDispatcher(LIST_MEALS);
        request.setAttribute("meals", dao.getAllMeals());
        view.forward(request, response);
    }
}
