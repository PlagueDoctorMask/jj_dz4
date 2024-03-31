package org.example;

import org.example.domen.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {

        Configuration configuration = new Configuration().configure();
        try(SessionFactory sessionFactory = configuration.buildSessionFactory()){
            InsertNewStudents(sessionFactory);
            FindById(sessionFactory,2);
            UpdateAge(sessionFactory);
            FindWithQL(sessionFactory);//запрос студентов старше 20-ти
            RemoveStudent(sessionFactory, 4);
            FindById(sessionFactory,4);

        }
    }

    private static void FindWithQL(SessionFactory sessionFactory) {
        try(Session session = sessionFactory.openSession()){
            session.createQuery("from Student where age > 20", Student.class).getResultList().forEach(System.out::println);
        }
    }

    private static void RemoveStudent(SessionFactory sessionFactory,int id) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx = session.beginTransaction();
            Student student = session.find(Student.class, id);
            session.remove(student);
            tx.commit();
        }
    }

    private static void UpdateAge(SessionFactory sessionFactory) {
        try(Session session = sessionFactory.openSession()){
            Transaction tx = session.beginTransaction();
            Student student = session.find(Student.class, 2);
            student.setAge(18);
            session.merge(student);
            tx.commit();
            System.out.println(student);
        }
    }

    private static void FindById(SessionFactory sessionFactory, int id) {
        try(Session session = sessionFactory.openSession()){
            Student student = session.find(Student.class, id);
            System.out.println(student);
        }
    }

    private static void InsertNewStudents(SessionFactory sessionFactory) {
        Student student = new Student(1, "Itachi", "Uchiha", 15);
        Student student1 = new Student(2, "Shisui", "Uchiha", 17);
        Student student2 = new Student(3, "Kakashi", "Hatake", 17);
        Student student3 = new Student(4, "Zoro", "Roronoa", 21);
        try(Session session = sessionFactory.openSession()){
            Transaction tx = session.beginTransaction();
            session.persist(student);
            session.persist(student1);
            session.persist(student2);
            session.persist(student3);
            tx.commit();
        }
    }
}