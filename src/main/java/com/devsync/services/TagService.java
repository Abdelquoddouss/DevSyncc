package com.devsync.services;

import com.devsync.model.Tag;
import com.devsync.repository.TagRepository;

import java.util.List;

public class TagService {
    private TagRepository tagRepository;

    public TagService() {
        tagRepository = new TagRepository();
    }

    public void addTag(Tag tag) {
        tagRepository.addTag(tag);
    }

    public void deleteTag(Long tagId) {
        tagRepository.deleteTag(tagId);
    }

    public List<Tag> findAllTags() {
        return tagRepository.findAll();
    }
}
