package task2.utils;

import task2.models.Direction;
import task2.models.Grade;
import task2.models.Detail;
import task2.models.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactoryUtil {

    private static SessionFactory factory;

    public HibernateSessionFactoryUtil() {}

    public static SessionFactory getSessionFactory() {
        if (factory == null) {
            try {
                Configuration configuration = new Configuration()
                        .configure("hibernate.cfg.xml")
                        .addAnnotatedClass(User.class)
                        .addAnnotatedClass(Detail.class)
                        .addAnnotatedClass(Grade.class)
                        .addAnnotatedClass(Direction.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties());
                factory = configuration.buildSessionFactory(builder.build());
            } catch (Exception e) {
                System.out.println("Поймано исключение: " + e);
            }
        }
        return factory;
    }

    public static void setSessionFactory(SessionFactory sessionFactory) {
        factory = sessionFactory;
    }
}
