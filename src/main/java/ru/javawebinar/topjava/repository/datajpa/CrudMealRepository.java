package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m WHERE m.id=:id AND m.user.id=:userId")
    int delete(@Param("id") Integer id, @Param("userId") Integer userId);

    Meal findByIdAndUserId(Integer id, Integer userId);

    List<Meal> findAllByUserId(Sort sort, Integer userId);

    List<Meal> findAllByUserIdAndDateTimeIsGreaterThanEqualAndDateTimeIsLessThan(Sort sort, Integer user_id, LocalDateTime dateTime, LocalDateTime dateTime2);

    @Transactional
    @Query("SELECT m FROM Meal m JOIN FETCH m.user WHERE m.id=:id AND m.user.id=:userId")
    Meal getWithUser(@Param("id") Integer id, @Param("userId") Integer userId);
}
