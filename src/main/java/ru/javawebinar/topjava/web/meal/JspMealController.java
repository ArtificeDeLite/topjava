package ru.javawebinar.topjava.web.meal;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Controller
@RequestMapping(value = "/meals")
public class JspMealController extends MealController {

    public JspMealController(MealService service) {
        super(service);
    }

    @GetMapping(params = "create")
    public String create(Model model) {
        model.addAttribute("meal", new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000));
        return "mealForm";
    }

    @GetMapping(params = {"update", "id"})
    public String update(Model model, @Param("id") int id) {
        Meal meal = get(id);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping(params = {"delete", "id"})
    public String delete(Model model, @Param("id") int id) {
        delete(id);
        return get(model);
    }

    @GetMapping
    public String get(Model model) {
        model.addAttribute("meals", getAll());
        return "meals";
    }

    @GetMapping(params = {"filter", "startDate", "endDate", "startTime", "endTime"})
    public String getFiltered(Model model, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate,
                              @Param("startTime") LocalTime startTime, @Param("endTime") LocalTime endTime) {
        model.addAttribute("meals", getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }

    @PostMapping
    public String update(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        if (StringUtils.isEmpty(request.getParameter("id"))) {
            create(meal);
        } else {
            update(meal, Integer.parseInt(request.getParameter("id")));
        }

        return "redirect:meals";
    }

}
