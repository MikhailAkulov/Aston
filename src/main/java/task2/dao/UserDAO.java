package task2.dao;

import task2.models.Detail;
import task2.models.Grade;
import task2.models.User;

import java.util.List;

public interface UserDAO {
    void saveUser(User user);
    User getUser(int id);
    void updateUser(User user);
    void deleteUser(int id);
    List<User> getAllUsers();

    void updateDetails(Detail detail);
    void deleteDetails(Detail detail);

    void saveGrade(Grade grade);
    Grade getGrade(int id);
    void deleteGrade(int id);
    List<Grade> getAllGrades();
}
