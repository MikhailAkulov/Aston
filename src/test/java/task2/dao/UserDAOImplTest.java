package task2.dao;

import org.junit.jupiter.api.*;
import task2.models.User;
import task2.utils.HibernateSessionFactoryUtil;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserDAOImplTest {

    private static UserDAOImpl userDAO;

    @Container
    private static final PostgreSQLContainer<?> postgres =
            new PostgreSQLContainer<>("postgres:15-alpine")
                    .withDatabaseName("testdb")
                    .withUsername("test")
                    .withPassword("test");

    @BeforeAll
    static void setUp() {
        postgres.start();

        // Настройка Hibernate для подключения к контейнеру
        Configuration config = new Configuration();
        config.configure("hibernate.cfg.xml");

        config.setProperty("hibernate.connection.url", postgres.getJdbcUrl());
        config.setProperty("hibernate.connection.username", postgres.getUsername());
        config.setProperty("hibernate.connection.password", postgres.getPassword());
        config.setProperty("hibernate.hbm2ddl.auto", "create-drop");

        // Переопределение фабрики в HibernateSessionFactoryUtil
        SessionFactory sessionFactory = config.buildSessionFactory();
        HibernateSessionFactoryUtil.setSessionFactory(sessionFactory);

        userDAO = new UserDAOImpl();
    }

    @AfterAll
    static void tearDown() {
        postgres.stop();
    }

    @Test
    @Order(1)
    void saveUserSuccess() {
        User user = new User("John", "john@test.com", 25);
        userDAO.saveUser(user);
        assertNotNull(user.getId());
    }

    @Test
    @Order(2)
    void getUserSuccess() {
        User user = new User("James", "james@test.com", 32);
        userDAO.saveUser(user);

        User fetched = userDAO.getUser(user.getId());
        assertNotNull(fetched);
        assertEquals("James", fetched.getName());
    }

    @Test
    @Order(3)
    void updateUserSuccess() {
        User user = new User("Bill", "bill@test.com", 16);
        userDAO.saveUser(user);

        user.setName("Billy");
        user.setEmail("billy@test.com");
        userDAO.updateUser(user);

        User updated = userDAO.getUser(user.getId());
        assertEquals("Billy", updated.getName());
    }

    @Test
    @Order(4)
    void deleteUserSuccess() {
        User user = new User("Kate", "kate@test.com", 22);
        userDAO.saveUser(user);

        userDAO.deleteUser(user.getId());
        User deleted = userDAO.getUser(user.getId());
        assertNull(deleted);
    }

    @Test
    @Order(5)
    void deleteUserFailed() {
        int NONEXISTENTID = 9999;

        // Проверка отсутствия пользователя
        User user = userDAO.getUser(NONEXISTENTID);
        assertNull(user);

        // Вызов метода не должен выбросить исключение
        assertDoesNotThrow(() -> userDAO.deleteUser(NONEXISTENTID));

        // Проверка на null после попытки удаления
        User afterDelete = userDAO.getUser(NONEXISTENTID);
        assertNull(afterDelete);
    }

    @Test
    @Order(6)
    void getAllUsersSuccess() {
        userDAO.saveUser(new User("Alex", "alex@test.com", 42));
        userDAO.saveUser(new User("Mary", "mary@test.com", 28));

        List<User> users = userDAO.getAllUsers();
        assertTrue(users.size() >= 2);
    }

    @Test
    @Order(7)
    void getAllUsersWithEmptyList() {
        // Очистка списка пользователей
        List<User> usersList = userDAO.getAllUsers();
        for (User u : usersList) {
            userDAO.deleteUser(u.getId());
        }

        // Получение пустого списка
        List<User> users = userDAO.getAllUsers();

        // Проверка, что список пуст
        assertNotNull(users, "Список пользователей не должен быть null");
        assertTrue(users.isEmpty(), "Список пользователей должен быть пустым");
    }
}