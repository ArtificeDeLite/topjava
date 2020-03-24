package ru.javawebinar.topjava.service.jdbc;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import java.util.Collections;
import java.util.Date;

import static ru.javawebinar.topjava.Profiles.JDBC;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(JDBC)
public class JdbcUserServiceTest extends AbstractUserServiceTest {

    @Test
    public void createAndGetWithoutRole() throws Exception {
        User newUser = new User(null, "New", "new@gmail.com", "newPass", 1555, false, new Date(), null);
        User created = service.create(newUser);
        Integer newId = created.getId();
        newUser.setId(newId);
        USER_MATCHER.assertMatch(created, newUser);
        USER_MATCHER.assertMatch(service.get(newId), newUser);
        USER_MATCHER.assertMatch(service.getByEmail("new@gmail.com"), newUser);
        USER_MATCHER.assertMatch(service.getAll(), ADMIN, USER, newUser);

        newUser.setRoles(Collections.singleton(Role.ROLE_USER));
        service.update(newUser);
        USER_MATCHER.assertMatch(service.get(newId), newUser);
    }
}