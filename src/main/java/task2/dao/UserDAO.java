package task2.dao;

import task2.models.Detail;
import task2.models.Direction;
import task2.models.Grade;
import task2.models.User;

import java.util.List;

public interface UserDAO {
    void saveUser(User user);
    User getUser(int id);
    void updateUser(User user);
    void deleteUser(int id);
    List<User> getAllUsers();

    void deleteDetails(Detail detail);

    void saveGrade(Grade grade);
    Grade getGrade(int id);
    void deleteGrade(int id);
    List<Grade> getAllGrades();

    void saveDirection(Direction direction);
    Direction getDirection(int id);
    void updateDirection(Direction direction);
    void deleteDirection(int id);
    List<Direction> getAllDirections();
}
