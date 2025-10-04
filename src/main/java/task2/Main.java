package task2;

import task2.service.UserServiceImpl;
import task2.utils.HibernateSessionFactoryUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    static final UserServiceImpl userService = new UserServiceImpl();

    static String helloMsg = "Добро пожаловать в сервис Пользователей!\n" +
            "Введите в консоли номер необходимого пункта:";

    static String menuMsg = "1.  Добавить нового пользователя;\n" +
            "2.  Получить пользователя;\n" +
            "3.  Обновить данные пользователя;\n" +
            "4.  Удалить пользователя;\n" +
            "5.  Показать список всех пользователей;\n" +
            "6.  Показать детальную информацию пользователя;\n" +
            "7.  Удалить детальную информацию пользователя;\n" +
            "8.  Показать список квалификаций;\n" +
            "9.  Добавить новую квалификацию;\n" +
            "10. Удалить квалификацию;\n" +
            "11. Показать квалификацию пользователя;\n" +
            "12. Присвоить пользователю квалификацию;\n" +
            "13. Показать список пользователей конкретной квалификации;\n" +
            "14. Показать список направлений обучения;\n" +
            "15. Добавить новое направление обучения;\n" +
            "16. Удалить направление обучения;\n" +
            "17. Показать направления обучения пользователя;\n" +
            "18. Добавить пользователю направление обучения;\n" +
            "19. Показать список пользователей определённого направления обучения;\n" +
            "20. Выход";

    static String continueMsg = "Выберите дальнейшее действие:";

    public static void main(String[] args) {
        System.out.println(helloMsg);
        System.out.println(menuMsg);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                switch (reader.readLine()) {
                    case "1" -> {
                        userService.saveUser();
                        System.out.println("\n" + continueMsg + "\n" + menuMsg);
                    }
                    case "2" -> {
                        userService.getUser();
                        System.out.println("\n" + continueMsg + "\n" + menuMsg);
                    }
                    case "3" -> {
                        userService.updateUser();
                        System.out.println("\n" + continueMsg + "\n" + menuMsg);
                    }
                    case "4" -> {
                        userService.deleteUser();
                        System.out.println("\n" + continueMsg + "\n" + menuMsg);
                    }
                    case "5" -> {
                        userService.getAllUsers();
                        System.out.println("\n" + continueMsg + "\n" + menuMsg);
                    }
                    case "6" -> {
                        userService.getUserDetails();
                        System.out.println("\n" + continueMsg + "\n" + menuMsg);
                    }
                    case "7" -> {
                        userService.deleteDetails();
                        System.out.println("\n" + continueMsg + "\n" + menuMsg);
                    }
                    case "8" -> {
                        userService.getAllGrades();
                        System.out.println("\n" + continueMsg + "\n" + menuMsg);
                    }
                    case "9" -> {
                        userService.saveGrade();
                        System.out.println("\n" + continueMsg + "\n" + menuMsg);
                    }
                    case "10" -> {
                        userService.deleteGrade();
                        System.out.println("\n" + continueMsg + "\n" + menuMsg);
                    }
                    case "11" -> {
                        userService.getUserGrade();
                        System.out.println("\n" + continueMsg + "\n" + menuMsg);
                    }
                    case "12" -> {
                        userService.assignGradeToUser();
                        System.out.println("\n" + continueMsg + "\n" + menuMsg);
                    }
                    case "13" -> {
                        userService.getGradeListOfUsers();
                        System.out.println("\n" + continueMsg + "\n" + menuMsg);
                    }
                    case "14" -> {
                        userService.getAllDirections();
                        System.out.println("\n" + continueMsg + "\n" + menuMsg);
                    }
                    case "15" -> {
                        userService.saveDirection();
                        System.out.println("\n" + continueMsg + "\n" + menuMsg);
                    }
                    case "16" -> {
                        userService.deleteDirection();
                        System.out.println("\n" + continueMsg + "\n" + menuMsg);
                    }
                    case "17" -> {
                        userService.getUserDirections();
                        System.out.println("\n" + continueMsg + "\n" + menuMsg);
                    }
                    case "18" -> {
                        userService.addLearningDirectionToUser();
                        System.out.println("\n" + continueMsg + "\n" + menuMsg);
                    }
                    case "19" -> {
                        userService.getUsersListByDirection();
                        System.out.println("\n" + continueMsg + "\n" + menuMsg);
                    }
                    case "20" -> {
                        System.out.println("Завершение работы сервиса");
                        HibernateSessionFactoryUtil.getSessionFactory().close();
                        System.exit(0);
                    }
                    default -> System.out.print("Необходимо ввести число соответствующее пункту меню: ");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
