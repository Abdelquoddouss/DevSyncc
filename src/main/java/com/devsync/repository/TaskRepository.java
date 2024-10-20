package com.devsync.repository;

import com.devsync.enums.TaskStatus;
import com.devsync.model.Task;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class TaskRepository {
    private EntityManagerFactory emf;

    public TaskRepository(EntityManagerFactory emf) {
        this.emf = Persistence.createEntityManagerFactory("DevSyncPU");
    }

    public void addTask(Task task) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(task);
        em.getTransaction().commit();
        em.close();
    }

    public List<Task> findAll() {
        EntityManager em = emf.createEntityManager();
        List<Task> tasks = em.createQuery("SELECT t FROM Task t", Task.class).getResultList();
        em.close();
        return tasks;
    }

    public Task findById(Long id) {
        EntityManager em = emf.createEntityManager();
        Task task = em.find(Task.class, id);
        em.close();
        return task;
    }

    public void deleteTask(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Task task = em.find(Task.class, id);
        em.remove(task);
        em.getTransaction().commit();
        em.close();
    }

    public void updateTaskStatus(Long id, TaskStatus status) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Task task = em.find(Task.class, id);
        task.setStatus(status);
        em.getTransaction().commit();
        em.close();
    }

    public List<Task> findTasksByUser(Long userId) {
        EntityManager em = emf.createEntityManager();
        List<Task> tasks = em.createQuery("SELECT t FROM Task t WHERE t.user.id = :userId", Task.class)
                .setParameter("userId", userId)
                .getResultList();
        em.close();
        return tasks;
    }
}
