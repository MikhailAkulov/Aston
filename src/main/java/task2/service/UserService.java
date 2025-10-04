package task2.service;

import task2.models.Detail;
import task2.models.Grade;
import task2.models.User;

import java.util.List;

public interface UserService {
    void saveUser();
    void getUser();
    void updateUser();
    void deleteUser();
    void getAllUsers();

    void getUserDetails();
    void deleteDetails();

    void saveGrade();
    void deleteGrade();
    void getAllGrades();
    void getUserGrade();
    void assignGradeToUser();
    void getGradeListOfUsers();

    void saveDirection();
    void deleteDirection();
    void getAllDirections();
    void getUserDirections();
    void addLearningDirectionToUser();
    void getUsersListByDirection();
}
