package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final String CREAT_TABLE_SQL = "CREATE TABLE IF NOT EXISTS users (\n" +
            "  `id` int NOT NULL AUTO_INCREMENT,\n" +
            "  `name` varchar(100) NOT NULL,\n" +
            "  `lastname` varchar(100) NOT NULL,\n" +
            "  `age` int NOT NULL,\n" +
            "  PRIMARY KEY (`id`)\n" +
            ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;";
    private static final String DROP_TABLE_SQL = "DROP TABLE IF EXISTS users;";

    private final HibernateUtil util;
    private SessionFactory sessionFactory;

    public UserDaoHibernateImpl() {
        util = new HibernateUtil();
        sessionFactory = util.getSessionFactory();
    }


    @Override
    public void createUsersTable() {

        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery(CREAT_TABLE_SQL).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception ignore) {

        }
    }

    @Override
    public void dropUsersTable() {
//        cleanUsersTable();
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createSQLQuery(DROP_TABLE_SQL).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception ignore) {

        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            System.out.println(session.save(new User(name, lastName, age)));
            session.getTransaction().commit();
        } catch (Exception ignore) {

        }


    }

    @Override
    public void removeUserById(long id) {
        try(Session session = sessionFactory.openSession()) {
            User user = session.find(User.class, id);
            session.remove(user);
            session.close();
        } catch (Exception ignore) {

        }

    }

    @Override
    public List<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        try(Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            users = (ArrayList<User>) session.createCriteria(User.class).list();
            session.getTransaction().commit();
        } catch (Exception ignore) {

        }
        return users;

    }

    @Override
    public void cleanUsersTable() {

        try(Session session = sessionFactory.openSession()) {

            session.beginTransaction();
            session.createSQLQuery("TRUNCATE TABLE users").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception ignore) {

        }

    }
}
