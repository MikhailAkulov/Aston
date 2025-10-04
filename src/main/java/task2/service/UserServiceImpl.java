package task2.service;

import task2.models.Detail;
import task2.models.Direction;
import task2.models.Grade;
import task2.models.User;
import task2.dao.UserDAOImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserDAOImpl userDAOImpl = new UserDAOImpl();
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public UserServiceImpl() {}

    @Override
    public void saveUser() {
        System.out.println("Добавление нового пользователя");
        User newUser = new User(validateName(), validateEmail(), validateAge());
        System.out.println("Желаете ввести детальную информацию (телефон и адрес)?");
        if (userSelectionRequest()) {
            Detail detail = new Detail(validatePhoneNumber(), validateAddress());
            newUser.setUserDetail(detail);
            detail.setUser(newUser);
        }
        userDAOImpl.saveUser(newUser);
    }

    @Override
    public void getUser() {
        System.out.print("Поиск пользователя\nВведите id: ");
        int userId = validateNumberInput();
        User foundUser = userDAOImpl.getUser(userId);
        if (foundUser == null) {
            System.out.println("Пользователь с указанным ID отсутствует в базе");
        } else {
            System.out.println("Искомый пользователь: " + foundUser);
        }
    }

    @Override
    public void updateUser() {
        System.out.print("Обновление данных\nВведите id пользователя: ");
        int userId = validateNumberInput();
        User updatableUser = userDAOImpl.getUser(userId);
        boolean changes = false;
        if (updatableUser != null) {
            System.out.println("Желаете обновить основную информацию (имя, возраст и почту)?");
            if (userSelectionRequest()) {
                updatableUser.setName(validateName());
                updatableUser.setEmail(validateEmail());
                updatableUser.setAge(validateAge());
                changes = true;
            }
            System.out.println("Желаете ввести или обновить детальную информацию (телефон и адрес)?");
            if (userSelectionRequest()) {
                Detail updatableDetail = updatableUser.getUserDetail();
                if (updatableDetail != null) {
                    updatableDetail.setPhoneNumber(validatePhoneNumber());
                    updatableDetail.setAddress(validateAddress());
                } else {
                    updatableDetail = new Detail(validatePhoneNumber(), validateAddress());
                    updatableUser.setUserDetail(updatableDetail);
                    updatableDetail.setUser(updatableUser);
                }
                changes = true;
            }
            if (changes) {
                userDAOImpl.updateUser(updatableUser);
            } else {
                System.out.println("Изменения не были внесены.");
            }
        } else {
            System.out.println("Пользователь с указанным ID отсутствует в базе");
        }
    }

    @Override
    public void deleteUser() {
        System.out.print("Удаление пользователя\nВведите id: ");
        int userId = validateNumberInput();
        User deletableUser = userDAOImpl.getUser(userId);
        if (deletableUser != null) {
            userDAOImpl.deleteUser(deletableUser.getId());
        } else {
            System.out.println("Пользователь с указанным ID отсутствует в базе");
        }
    }

    @Override
    public void getAllUsers() {
        List<User> users = userDAOImpl.getAllUsers();
        System.out.println("Список всех пользователей:");
        checkListAndPrint(users);
    }

    @Override
    public void getUserDetails() {
        System.out.print("Получение детальной информации\nВведите id пользователя: ");
        int userId = validateNumberInput();
        User foundUser = userDAOImpl.getUser(userId);
        if (foundUser == null) {
            System.out.println("Пользователь с указанным ID отсутствует в базе");
        } else {
            Detail foundDetail = foundUser.getUserDetail();
            if (foundDetail != null) {
                System.out.println("Детальная информация пользователя: " + foundDetail);
            } else {
                System.out.println("Детальная информация пользователя отсутствует");
            }
        }
    }

    @Override
    public void deleteDetails() {
        System.out.print("Удаление детальной информации\nВведите id пользователя: ");
        int userId = validateNumberInput();
        User foundUser = userDAOImpl.getUser(userId);
        if (foundUser == null) {
            System.out.println("Пользователь с указанным ID отсутствует в базе");
        } else {
            Detail deletableDetail = foundUser.getUserDetail();
            if (deletableDetail != null) {
                foundUser.setUserDetail(null);
                userDAOImpl.updateUser(foundUser);
                userDAOImpl.deleteDetails(deletableDetail);
            } else {
                System.out.println("Детальная информация пользователя отсутствует");
            }
        }
    }

    @Override
    public void saveGrade() {
        List<Grade> grades = userDAOImpl.getAllGrades();
        System.out.print("Добавление квалификации\nВведите наименование квалификации: ");
        String newGrade = validateStringInput();
        if (grades.isEmpty()) {
            userDAOImpl.saveGrade(new Grade(newGrade));
        } else {
            List<String> levels = new ArrayList<>();
            for (Grade grade : grades) {
                levels.add(grade.getGradeLevel());
            }
            if (!levels.contains(newGrade)) {
                userDAOImpl.saveGrade(new Grade(newGrade));
            } else {
                System.out.println("Указанная квалификация уже есть в списке");
            }
        }
    }

    @Override
    public void deleteGrade() {
        System.out.print("Удаление квалификации\nВведите id квалификации: ");
        int gradeId = validateNumberInput();
        Grade deletableGrade = userDAOImpl.getGrade(gradeId);
        if (deletableGrade != null) {
            List<User> users = deletableGrade.getUsers();
            if (!users.isEmpty()) {
                for (User user : users) {
                    user.setGrade(null);
                    userDAOImpl.updateUser(user);
                }
            }
            userDAOImpl.deleteGrade(deletableGrade.getId());
        } else {
            System.out.println("Квалификация с указанным ID отсутствует в базе");
        }
    }

    @Override
    public void getAllGrades() {
        List<Grade> grades = userDAOImpl.getAllGrades();
        System.out.println("Список квалификаций:");
        checkListAndPrint(grades);
    }

    @Override
    public void getUserGrade() {
        System.out.print("Квалификация пользователя\nВведите id пользователя: ");
        int userId = validateNumberInput();
        User foundUser = userDAOImpl.getUser(userId);
        if (foundUser != null) {
            Grade grade = foundUser.getGrade();
            if (grade != null) {
                System.out.println("Квалификация пользователя " + foundUser.getName() + " : " + grade.getGradeLevel());
            } else {
                System.out.println("У пользователя с данным ID квалификация не указана.");
            }
        } else {
            System.out.println("Пользователь с указанным ID отсутствует в базе");
        }
    }

    @Override
    public void assignGradeToUser() {
        System.out.print("Присвоение квалификации пользователю\nВведите id квалификации: ");
        int gradeId = validateNumberInput();
        Grade grade = userDAOImpl.getGrade(gradeId);
        if (grade != null) {
            System.out.println("Выбрана квалификация: " + grade.getGradeLevel());
            System.out.print("Введите id пользователя: ");
            int userId = validateNumberInput();
            User user = userDAOImpl.getUser(userId);
            if (user != null) {
                grade.addUserToGrade(user);
                userDAOImpl.updateUser(user);
            } else {
                System.out.println("Пользователь с указанным ID отсутствует в базе");
            }
        } else {
            System.out.println("Квалификация с указанным ID отсутствует в базе");
        }
    }

    @Override
    public void getGradeListOfUsers() {
        System.out.print("Получение квалификационного списка пользователей\nВведите id квалификации: ");
        int id = validateNumberInput();
        Grade grade = userDAOImpl.getGrade(id);
        if (grade != null) {
            System.out.println("Выбрана квалификация: " + grade.getGradeLevel());
            List<User> users = grade.getUsers();
            checkListAndPrint(users);
        } else {
            System.out.println("Квалификация с указанным ID отсутствует в базе");
        }
    }

    @Override
    public void saveDirection() {
        System.out.print("Добавление направления обучения\nВведите наименование: ");
        String newDirection = validateStringInput();
        List<Direction> directions = userDAOImpl.getAllDirections();
        if (directions.isEmpty()) {
            userDAOImpl.saveDirection(new Direction(newDirection));
        } else {
            List<String> directionsNames = new ArrayList<>();
            for (Direction d : directions) {
                directionsNames.add(d.getLearningDirection());
            }
            if (!directionsNames.contains(newDirection)) {
                userDAOImpl.saveDirection(new Direction(newDirection));
            } else {
                System.out.println("Указанное направление уже есть в списке");
            }
        }
    }

    @Override
    public void deleteDirection() {
        System.out.print("Удаление направления обучения\nВведите id направления: ");
        int directionId = validateNumberInput();
        Direction deletableDirection = userDAOImpl.getDirection(directionId);
        if (deletableDirection != null) {
            userDAOImpl.deleteDirection(directionId);
        } else {
            System.out.println("Направление обучения с указанным ID отсутствует в базе");
        }
    }

    @Override
    public void getAllDirections() {
        List<Direction> directions = userDAOImpl.getAllDirections();
        System.out.println("Список направлений обучения:");
        checkListAndPrint(directions);
    }

    @Override
    public void getUserDirections() {
        System.out.print("Направления обучения пользователя\nВведите id пользователя: ");
        int userId = validateNumberInput();
        User foundUser = userDAOImpl.getUser(userId);
        System.out.println(foundUser);
        if (foundUser != null) {
            List<Direction> directions = foundUser.getDirections();
            checkListAndPrint(directions);
        } else {
            System.out.println("Пользователь с указанным ID отсутствует в базе");
        }
    }

    @Override
    public void addLearningDirectionToUser() {
        System.out.print("Добавление направления обучения пользователю\nВведите id направления: ");
        int directionId = validateNumberInput();
        Direction direction = userDAOImpl.getDirection(directionId);
        if (direction != null) {
            System.out.println("Выбрано направление: " + direction.getLearningDirection());
            System.out.print("Введите id пользователя: ");
            int userId = validateNumberInput();
            User user = userDAOImpl.getUser(userId);
            if (user != null) {
                // 1
//                user.addDirectionToUser(direction);
//                userDAOImpl.updateUser(user);
                // 2
                direction.addUserToDirection(user);
                userDAOImpl.updateDirection(direction);
            } else {
                System.out.println("Пользователь с указанным ID отсутствует в базе");
            }
        } else {
            System.out.println("Направление обучения с указанным ID отсутствует в базе");
        }
    }

    @Override
    public void getUsersListByDirection() {
        System.out.print("Получение списка пользователей\nВведите id направления обучения: ");
        int id = validateNumberInput();
        Direction direction = userDAOImpl.getDirection(id);
        if (direction != null) {
            System.out.println("Выбрано направление: " + direction.getLearningDirection());
            List<User> users = direction.getUsers();
            checkListAndPrint(users);
        } else {
            System.out.println("Направление обучения с указанным ID отсутствует в базе");
        }
    }

    //region Вспомогательные методы для валидации консольного ввода
    private static String validateEmail() {
        System.out.print("Введите email: ");
        String email = "";
        try {
            email = reader.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        while (!User.checkEmail(email)) {
            System.out.print("Попробуйте ещё разок: ");
            try {
                email = reader.readLine();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return email;
    }

    private static String validateName() {
        System.out.print("Введите имя: ");
        String name = "";
        try {
            name = reader.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        while (!User.checkName(name)) {
            System.out.print("Попробуйте ещё разок: ");
            try {
                name = reader.readLine();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return name;
    }

    private static Integer validateAge() {
        System.out.print("Введите возраст: ");
        int age = validateNumberInput();
        while (!User.checkAge(age)) {
            System.out.print("Попробуйте ещё разок: ");
            try {
                age = Integer.parseInt(reader.readLine());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return age;
    }

    private static Integer validateNumberInput() {
        String line = "";
        try {
            line = reader.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        while (!line.matches("\\d+")) {
            System.out.print("Только положительное число! Попробуйте ещё разок: ");
            try {
                line = reader.readLine();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return Integer.parseInt(line);
    }

    private static String validatePhoneNumber() {
        System.out.print("Введите номер телефона: ");
        String phoneNumber = "";
        try {
            phoneNumber = reader.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        while (!phoneNumber.matches("\\d{1}-\\d{3}-\\d{3}-\\d{2}-\\d{2}")) {
            System.out.print("Используйте шаблон X-XXX-XXX-XX-XX! Попробуйте ещё разок: ");
            try {
                phoneNumber = reader.readLine();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return phoneNumber;
    }

    private static String validateAddress() {
        System.out.print("Введите адрес: ");
        String address = "";
        try {
            address = reader.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return address;
    }

    private static boolean userSelectionRequest() {
        System.out.print("Введите Y или N: ");
        String choice = "";
        try {
            choice = reader.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        while (!choice.equalsIgnoreCase("Y") && !choice.equalsIgnoreCase("N")) {
            System.out.print("Введите одну букву Y или N: ");
            try {
                choice = reader.readLine();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return choice.equalsIgnoreCase("y");
    }

    private static String validateStringInput() {
        String grade = "";
        try {
            grade = reader.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        while (!grade.matches("^[A-Za-z]+$")) {
            System.out.print("Используйте только латинские вуквы! Попробуйте ещё разок: ");
            try {
                grade = reader.readLine();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return grade.trim().toUpperCase();
    }

    private <T> void checkListAndPrint(List<T> list) {
        if (list.isEmpty()) {
            System.out.println("Список пуст.");
        } else {
            list.forEach(System.out::println);
        }
    }
    //endregion
}
