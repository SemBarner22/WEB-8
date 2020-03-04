package ru.itmo.wp.service;
import ru.itmo.wp.domain.Role;
import ru.itmo.wp.domain.Tag;
import ru.itmo.wp.repository.TagRepository;

public class TagService {
    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    Tag findByName(String name) {
        return tagRepository.findByName(name);
    };
}
