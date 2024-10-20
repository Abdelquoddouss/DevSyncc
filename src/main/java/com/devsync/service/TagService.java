package com.devsync.service;

import com.devsync.model.Tag;
import com.devsync.repository.TagRepository;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class TagService {


    private TagRepository tagRepository;

     public TagService(EntityManagerFactory emf) {
        this.tagRepository = new TagRepository(emf);
     }


    public void addTag(Tag tag){
        tagRepository.addTag(tag);
    }

    public Tag findById(Long id){
        return tagRepository.findById(id);
     }

    public List<Tag> findAll(){
        return tagRepository.findAll();
        }


        public void deleteTag(Long id){
        tagRepository.deleteTag(id);
     }




    }
