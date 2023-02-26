package org.da477.springsecurity.service;

import org.da477.springsecurity.model.Developer;
import org.da477.springsecurity.repository.DeveloperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeveloperService {

    private static final Sort SORT_NAME = Sort.by(Sort.Direction.ASC, "firstname");

    private final DeveloperRepository repository;

    @Autowired
    public DeveloperService(DeveloperRepository repository) {
        this.repository = repository;
    }

    public List<Developer> findAll() {
        return repository.findAll(SORT_NAME);
    }

    public Developer getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void add(Developer developer) {
        repository.save(developer);
    }
}
