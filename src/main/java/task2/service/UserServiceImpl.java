package task2.service;

import task2.models.Detail;
import task2.models.Grade;
import task2.models.User;
import task2.dao.UserDAOImpl;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDAOImpl userDAOImpl = new UserDAOImpl();

    public UserServiceImpl() {}

    @Override
    public void saveUser(User user) {
        userDAOImpl.saveUser(user);
    }

    @Override
    public User getUser(int id) {
        return userDAOImpl.getUser(id);
    }

    @Override
    public void updateUser(User user) {
        userDAOImpl.updateUser(user);
    }

    @Override
    public void deleteUser(int id) {
        userDAOImpl.deleteUser(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userDAOImpl.getAllUsers();
    }

    @Override
    public void updateDetails(Detail detail) {
        userDAOImpl.updateDetails(detail);
    }

    @Override
    public void deleteDetails(Detail detail) {
        userDAOImpl.deleteDetails(detail);
    }

    @Override
    public void saveGrade(Grade grade) {
        userDAOImpl.saveGrade(grade);
    }

    @Override
    public Grade getGrade(int id) {
        return userDAOImpl.getGrade(id);
    }

    @Override
    public void deleteGrade(int id) {
        userDAOImpl.deleteGrade(id);
    }

    @Override
    public List<Grade> getAllGrades() {
        return userDAOImpl.getAllGrades();
    }
}
