package task2;

import task2.models.Detail;
import task2.models.Grade;
import task2.models.User;
import task2.service.UserServiceImpl;
import task2.utils.HibernateSessionFactoryUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Main {

    static UserServiceImpl userService = new UserServiceImpl();
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    static String helloMsg = "Добро пожаловать в сервис Пользователей!\n" +
            "Введите в консоли номер необходимого пункта:";

    static String menuMsg = "1. Добавить нового пользователя;\n" +
            "2.  Найти пользователя;\n" +
            "3.  Обновить данные пользователя;\n" +
            "4.  Удалить пользователя;\n" +
            "5.  Показать список всех пользователей;\n" +
            "6.  Добавить нового пользователя c детальной информацией;\n" +
            "7.  Показать детальную информацию пользователя;\n" +
            "8.  Добавить детальную информацию пользователю;\n" +
            "9.  Изменить детальную информацию пользователя;\n" +
            "10. Удалить детальную информацию пользователя;\n" +
            "11. Добавить новую квалификацию;\n" +
            "12. Показать список квалификаций;\n" +
            "13. Присвоить пользователю квалификацию;\n" +
            "14. Показать квалификацию пользователя;\n" +
            "15. Показать список пользователей конкретной квалификации;\n" +
            "16. Удалить квалификацию;\n" +
            "20. Выход";

    static String continueMsg = "Выберите дальнейшее действие:";

    public static void main(String[] args) {
        System.out.println(helloMsg);
        System.out.println(menuMsg);

        while (true) {
            int choice = validateNumberInput();
            switch (choice) {
                case 1:
                    System.out.println("Добавление нового пользователя");
                    userService.saveUser(addNewUser());
                    System.out.println(continueMsg + "\n" + menuMsg);
                    break;
                case 2:
                    System.out.print("Поиск пользователя\nВведите id: ");
                    notNullOutput(userService.getUser(validateNumberInput()));
                    System.out.println(continueMsg + "\n" + menuMsg);
                    break;
                case 3:
                    System.out.print("Обновление данных\nВведите id пользователя: ");
                    changeUser(userService.getUser(validateNumberInput()));
                    System.out.println(continueMsg + "\n" + menuMsg);
                    break;
                case 4:
                    System.out.print("Удаление пользователя\nВведите id: ");
                    userService.deleteUser(validateNumberInput());
                    System.out.println(continueMsg + "\n" + menuMsg);
                    break;
                case 5:
                    System.out.println("Список всех пользователей:");
                    printUsersList(userService.getAllUsers());
                    System.out.println(continueMsg + "\n" + menuMsg);
                    break;
                case 6:
                    System.out.println("Добавление нового пользователя с детальной информацией");
                    userService.saveUser(addNewUserWithDetails());
                    System.out.println(continueMsg + "\n" + menuMsg);
                    break;
                case 7:
                    System.out.print("Получение детальной информации\nВведите id пользователя: ");
                    printUserDetails(userService.getUser(validateNumberInput()).getUserDetail());
                    System.out.println(continueMsg + "\n" + menuMsg);
                    break;
                case 8:
                    System.out.print("Добавление детальной информации\nВведите id пользователя: ");
                    userService.updateUser(addDetailsToUser(userService.getUser(validateNumberInput())));
                    System.out.println(continueMsg + "\n" + menuMsg);
                    break;
                case 9:
                    System.out.print("Изменение детальной информации\nВведите id пользователя: ");
                    userService.updateDetails(updateUserDetails(userService.getUser(validateNumberInput())));
                    System.out.println(continueMsg + "\n" + menuMsg);
                    break;
                case 10:
                    System.out.print("Удаление детальной информации\nВведите id пользователя: ");
                    User user1 = userService.getUser(validateNumberInput());
                    userService.deleteDetails(deleteUserDetails(user1));
                    System.out.println(continueMsg + "\n" + menuMsg);
                    break;
                case 11:
                    System.out.println("Добавление квалификации");
                    userService.saveGrade(createNewGrade());
                    System.out.println(continueMsg + "\n" + menuMsg);
                    break;
                case 12:
                    System.out.println("Список квалификаций:");
                    printGradesList(userService.getAllGrades());
                    System.out.println(continueMsg + "\n" + menuMsg);
                    break;
                case 13:
                    System.out.print("Присвоение квалификации пользователю\nВведите id квалификации: ");
                    Grade grade = userService.getGrade(validateNumberInput());
                    System.out.print("Введите id пользователя: ");
                    User user2 = userService.getUser(validateNumberInput());
                    assignGrade(grade, user2);
                    System.out.println(continueMsg + "\n" + menuMsg);
                    break;
                case 14:
                    System.out.print("Квалификация пользователя\nВведите id пользователя: ");
                    printUserGrade(userService.getUser(validateNumberInput()));
                    System.out.println(continueMsg + "\n" + menuMsg);
                    break;
                case 15:
                    System.out.print("Список пользователей конкретной квалификации\nВведите id квалификации: ");
                    checkGrade(userService.getGrade(validateNumberInput()));
                    System.out.println(continueMsg + "\n" + menuMsg);
                    break;
                case 16:
                    System.out.print("Удаление квалификации\nВведите id квалификации: ");
                    checkGradeBeforeDelete(userService.getGrade(validateNumberInput()));
                    System.out.println(continueMsg + "\n" + menuMsg);
                    break;
                case 20:
                    System.out.println("Завершение работы сервиса");
                    try {
                        reader.close();
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                    HibernateSessionFactoryUtil.getSessionFactory().close();
                    System.exit(0);
                    break;
                default:
                    System.out.print("Необходимо ввести цифру соответствующую интересующему пункту: ");
            }
        }
    }

    //region Вспомогательные методы консольного интерфейса. Основной функционал.
    private static User addNewUser() {
        return new User(validateName(), validateEmail(), validateAge());
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

    private static void notNullOutput(User user) {
        if (user == null) {
            System.out.println("Пользователь с указанным id отсутствует в базе");
        } else {
            System.out.println("Искомый пользователь: " + user);
        }
    }

    private static void changeUser(User user) {
        if (user == null) {
            System.out.println("Пользователь с указанным id отсутствует в базе");
        } else {
            user.setName(validateName());
            user.setEmail(validateEmail());
            user.setAge(validateAge());
            userService.updateUser(user);
        }
    }

    private static void printUsersList(List<User> users) {
        if (users.isEmpty()) {
            System.out.println("Список пуст.");
        } else {
            users.forEach(System.out::println);
        }
    }
    //endregion

    //region Вспомогательные методы. Сущность Detail (@OneToOne - relation)
    private static User addNewUserWithDetails() {
        User user = addNewUser();
        Detail detail = createNewDetails();
        user.setUserDetail(detail);
        detail.setUser(user);
        return user;
    }

    private static User addDetailsToUser(User user) {
        if (user == null) {
            System.out.println("Пользователь с указанным id отсутствует в базе");
        } else {
            Detail detail = createNewDetails();
            user.setUserDetail(detail);
            detail.setUser(user);
        }
        return user;
    }

    private static Detail updateUserDetails(User user) {
        Detail detail = null;
        if (user == null) {
            System.out.println("Пользователь с указанным id отсутствует в базе");
        } else {
            detail = user.getUserDetail();
            detail.setPhoneNumber(validatePhoneNumber());
            detail.setAddress(validateAddress());
        }
        return detail;
    }

    private static Detail createNewDetails() {
        return new Detail(validatePhoneNumber(), validateAddress());
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

    private static Detail deleteUserDetails(User user) {
        Detail detail = null;
        if (user == null) {
            System.out.println("Пользователь с указанным id отсутствует в базе");
        } else {
            detail = user.getUserDetail();
            user.setUserDetail(null);
            userService.updateUser(user);
        }
        return detail;
    }

    private static void printUserDetails(Detail detail) {
        if (detail == null) {
            System.out.println("Детальная информация пользователя отсутствует в базе");
        } else {
            System.out.println(detail);
        }
    }
    //endregion

    //region Вспомогательные методы. Сущность Grade (@ManyToOne - relation)
    private static Grade createNewGrade() {
        return new Grade(validateGrade());
    }

    private static String validateGrade() {
        System.out.print("Введите наименование квалификации: ");
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
        return grade;
    }

    private static void printGradesList(List<Grade> grades) {
        if (grades.isEmpty()) {
            System.out.println("Список пуст. Добавьте квалификацию");
        } else {
            grades.forEach(System.out::println);
        }
    }

    private static void assignGrade(Grade grade, User user) {
        if (grade == null) {
            System.out.println("Квалификация с указанным id отсутствует в базе");
        }
        if (user == null) {
            System.out.println("Пользователь с указанным id отсутствует в базе");
        }
        else {
            grade.addUserToGrade(user);
            userService.updateUser(user);
        }
    }

    private static void printUserGrade(User user) {
        if (user == null) {
            System.out.println("Пользователь с указанным id отсутствует в базе");
        } else {
            System.out.println(user.getGrade());
        }
    }

    private static void checkGrade(Grade grade) {
        if (grade == null) {
            System.out.println("Квалификация с указанным id отсутствует в базе");
        } else {
           printUsersOfGrade(grade.getUsers());
        }
    }

    private static void printUsersOfGrade(List<User> users) {
        if (users.isEmpty()) {
            System.out.println("Список пуст. Присвойте кому-нибудь данную квалификацию");
        } else {
            users.forEach(System.out::println);
        }
    }

    private static void checkGradeBeforeDelete(Grade grade) {
        if (grade == null) {
            System.out.println("Квалификация с указанным id отсутствует в базе");
        } else {
            List<User> users = grade.getUsers();
            if (!users.isEmpty()) {
                for (User user : users) {
                    user.setGrade(null);
                    userService.updateUser(user);
                }
            }
            userService.deleteGrade(grade.getId());
        }
    }
    //endregion
}

