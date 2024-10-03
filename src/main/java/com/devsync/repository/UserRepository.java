package com.devsync.repository;

import com.devsync.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class UserRepository {
    private EntityManagerFactory emf;

    public UserRepository( ) {
        this.emf = Persistence.createEntityManagerFactory("DevSyncPU");
    }

    public void addUser(User user) {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            em.close();

    }


    public List<User> findAll() {
        EntityManager em = emf.createEntityManager();
        List<User> users = em.createQuery("SELECT u FROM User u", User.class).getResultList();

        em.close();
        return users;
    }


    public void updateUser(User user) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(user);  // Use merge for updating
        em.getTransaction().commit();
        em.close();
    }

    public void deleteUser(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        User user = em.find(User.class, id);
        if (user != null) {
            em.remove(user);
        }
        em.getTransaction().commit();
        em.close();
    }

    public User findById(Long id) {
        EntityManager em = emf.createEntityManager();
        User user = em.find(User.class, id);
        em.close();
        return user;
    }


}
