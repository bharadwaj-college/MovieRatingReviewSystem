package com.movierating.movieratingreviewsystem.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class HibernateTest {

    public static void main(String[] args) {

        SessionFactory factory = null;
        Session session = null;

        try {
            System.out.println("Starting Hibernate...");

            factory = HibernateUtil.getSessionFactory();

            session = factory.openSession();

            System.out.println("Database Connected Successfully!");

            Long users = session.createQuery(
                    "SELECT COUNT(u) FROM User u",
                    Long.class
            ).getSingleResult();

            Long genres = session.createQuery(
                    "SELECT COUNT(g) FROM Genre g",
                    Long.class
            ).getSingleResult();

            Long movies = session.createQuery(
                    "SELECT COUNT(m) FROM Movie m",
                    Long.class
            ).getSingleResult();

            Long reviews = session.createQuery(
                    "SELECT COUNT(r) FROM Review r",
                    Long.class
            ).getSingleResult();

            System.out.println("\n----- DATABASE RECORDS -----");
            System.out.println("Users   : " + users);
            System.out.println("Genres  : " + genres);
            System.out.println("Movies  : " + movies);
            System.out.println("Reviews : " + reviews);

            System.out.println("\nHibernate Test Successful!");

        } catch (Exception e) {

            System.out.println("Hibernate Connection Failed!");
            e.printStackTrace();

        } finally {

            if (session != null && session.isOpen()) {
                session.close();
            }

            if (factory != null) {
                HibernateUtil.shutdown();
            }
        }
    }
}
