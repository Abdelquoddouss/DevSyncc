package com.devsync.repository;

import com.devsync.model.Tag;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class TagRepository {

    private static EntityManagerFactory emf;

    public TagRepository(){
        this.emf = Persistence.createEntityManagerFactory("DevSyncPU");
    }

    public void addTag(Tag tag){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(tag);
        em.getTransaction().commit();
        em.close();
    }

    public List<Tag> findAll(){
        EntityManager em = emf.createEntityManager();
        List<Tag> tags = em.createQuery("SELECT t FROM Tag t", Tag.class).getResultList();
        em.close();
        return tags;
    }

    // Méthode rendue non statique
    public Tag findById(Long id){
        EntityManager em = emf.createEntityManager();
        Tag tag = em.find(Tag.class, id);
        em.close();
        return tag;
    }

    public void deleteTag(Long id){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Tag tag = em.find(Tag.class,id);
        em.remove(tag);
        em.getTransaction().commit();
        em.close();
    }
}
