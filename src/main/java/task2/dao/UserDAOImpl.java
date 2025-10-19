package task2.dao;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import task2.models.Detail;
import task2.models.Direction;
import task2.models.Grade;
import task2.models.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import task2.utils.HibernateSessionFactoryUtil;

import java.util.List;

public class UserDAOImpl implements UserDAO {

    private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);
    private final SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();

    @Override
    public void saveUser(User user) {
        logger.info("Attempting to add a new user.");
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            logger.info("User saved successfully. ID: {}.", user.getId());
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error("Saving user failed.", e);
            throw new RuntimeException("Catching an exception when adding a user" + e.getMessage());
        }
    }

    @Override
    public User getUser(int id) {
        logger.info("Attempting to get user by id.");
        try (Session session = sessionFactory.openSession()) {
            User user = session.get(User.class, id);
            if (user != null) {
                logger.info("User with ID: {} successfully retrieved.", user.getId());
            } else {
                logger.warn("User with ID: {} not found.", id);
            }
            return user;
        } catch (Exception e) {
            logger.error("Failed to get user by ID.", e);
            throw new RuntimeException("Catching an exception when searching for a user: " + e.getMessage());
        }
    }

    @Override
    public void updateUser(User user) {
        logger.info("Attempting to update user.");
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.merge(user);
            transaction.commit();
            logger.info("User successfully updated: {}", user);
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error("Updating user failed.", e);
            throw new RuntimeException("Catching an exception when updating a user:" + e.getMessage());
        }
    }

    @Override
    public void deleteUser(int id) {
        logger.info("Attempting to delete user by ID.");
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
                transaction.commit();
                logger.info("User with ID: {} successfully deleted.", id);
            } else {
                transaction.rollback();
                logger.warn("Attempt to delete user with non-existent ID: {}", id);
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error("Deleting user failed.", e);
            throw new RuntimeException("Catching an exception when deleting a user: " + e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        logger.info("Trying to get a list of users.");
        try (Session session = sessionFactory.openSession()) {
            List<User> usersList = session.createQuery("from User", User.class).getResultList();
            if (!usersList.isEmpty()) {
                logger.info("Successfully found {} users", usersList.size());
            } else {
                logger.warn("Users list is empty");
            }
            return usersList;
        } catch (Exception e) {
            logger.error("Failed to get user list.", e);
            throw new RuntimeException("Catching an exception when getting a list of users: " + e.getMessage());
        }
    }

    @Override
    public void deleteDetails(Detail detail) {
        logger.info("Attempting to delete user's details");
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(detail);
            transaction.commit();
            logger.info("User's details successfully deleted.");
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error("Deleting user's details failed.", e);
            throw new RuntimeException("Catching an exception when deleting a user's details: " + e.getMessage());
        }
    }

    @Override
    public void saveGrade(Grade grade) {
        logger.info("Attempting to save grade.");
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(grade);
            transaction.commit();
            logger.info("Grade saved successfully. ID: {}.", grade.getId());
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error("Saving grade failed.", e);
            throw new RuntimeException("Catching an exception when saving a grade: " + e.getMessage());
        }
    }

    @Override
    public Grade getGrade(int id) {
        logger.info("Attempting to get grade by id.");
        try (Session session = sessionFactory.openSession()) {
            Grade grade = session.get(Grade.class, id);
            if (grade != null) {
                logger.info("Grade with ID: {} successfully retrieved.", grade.getId());
            } else {
                logger.warn("Grade with ID: {} not found.", id);
            }
            return grade;
        } catch (Exception e) {
            logger.error("Failed to get grade by ID.", e);
            throw new RuntimeException("Catching an exception when searching for a grade: " + e.getMessage());
        }
    }

    @Override
    public void deleteGrade(int id) {
        logger.info("Attempting to delete grade by ID.");
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Grade grade = session.get(Grade.class, id);
            if (grade != null) {
                session.delete(grade);
                transaction.commit();
                logger.info("Grade with ID: {} successfully deleted.", id);
            } else {
                transaction.rollback();
                logger.warn("Attempt to delete grade with non-existent ID: {}", id);
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error("Deleting grade failed.", e);
            throw new RuntimeException("Catching an exception when deleting a grade: " + e.getMessage());
        }
    }

    @Override
    public List<Grade> getAllGrades() {
        logger.info("Trying to get a list of grades.");
        try (Session session = sessionFactory.openSession()) {
            List<Grade> gradesList = session.createQuery("from Grade", Grade.class).getResultList();
            if (!gradesList.isEmpty()) {
                logger.info("Successfully found {} grades", gradesList.size());
            } else {
                logger.warn("Grades list is empty");
            }
            return gradesList;
        } catch (Exception e) {
            logger.error("Failed to get grades list.", e);
            throw new RuntimeException("Catching an exception when getting a list of grades: " + e.getMessage());
        }
    }

    @Override
    public void saveDirection(Direction direction) {
        logger.info("Attempting to add a new direction of learning.");
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(direction);
            transaction.commit();
            logger.info("Direction saved successfully. ID: {}.", direction.getId());
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error("Saving direction failed.", e);
            throw new RuntimeException("Catching an exception when adding a direction of learning" + e.getMessage());
        }
    }

    @Override
    public Direction getDirection(int id) {
        logger.info("Attempting to get learning direction by id.");
        try (Session session = sessionFactory.openSession()) {
            Direction direction = session.get(Direction.class, id);
            if (direction != null) {
                logger.info("Learning direction with ID: {} successfully retrieved.", direction.getId());
            } else {
                logger.warn("Learning direction with ID: {} not found.", id);
            }
            return direction;
        } catch (Exception e) {
            logger.error("Failed to get learning direction by ID.", e);
            throw new RuntimeException("Catching an exception when searching for a direction: " + e.getMessage());
        }
    }

    @Override
    public void updateDirection(Direction direction) {
        logger.info("Attempting to update learning direction.");
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(direction);
            transaction.commit();
            logger.info("Learning direction successfully updated: {}", direction);
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error("Updating learning direction failed.", e);
            throw new RuntimeException("Catching an exception when updating a direction:" + e.getMessage());
        }
    }

    @Override
    public void deleteDirection(int id) {
        logger.info("Attempting to delete learning direction by ID.");
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Direction direction = session.get(Direction.class, id);
            if (direction != null) {
                session.delete(direction);
                transaction.commit();
                logger.info("Learning direction with ID: {} successfully deleted.", id);
            } else {
                transaction.rollback();
                logger.warn("Attempt to delete learning direction with non-existent ID: {}", id);
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error("Deleting learning direction failed.", e);
            throw new RuntimeException("Catching an exception when deleting a direction: " + e.getMessage());
        }
    }

    @Override
    public List<Direction> getAllDirections() {
        logger.info("Trying to get a list of learning directions.");
        try (Session session = sessionFactory.openSession()) {
            List<Direction> directionsList = session.createQuery("from Direction", Direction.class).getResultList();
            if (!directionsList.isEmpty()) {
                logger.info("Successfully found {} learning directions", directionsList.size());
            } else {
                logger.warn("Learning directions list is empty");
            }
            return directionsList;
        } catch (Exception e) {
            logger.error("Failed to get learning directions list.", e);
            throw new RuntimeException("Catching an exception when getting a list of directions: " + e.getMessage());
        }
    }
}
