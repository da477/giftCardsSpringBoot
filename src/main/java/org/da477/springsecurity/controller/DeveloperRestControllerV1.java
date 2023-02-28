package org.da477.springsecurity.controller;

import org.da477.springsecurity.model.Developer;
import org.da477.springsecurity.repository.DeveloperRepository;
import org.da477.springsecurity.service.DeveloperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1/developers")
public class DeveloperRestControllerV1 {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private DeveloperService devService;

    @GetMapping
    public List<Developer> getAll() {
        log.info("getAll()");
        return devService.findAll();
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasAuthority('developer:read')")
//    @PreAuthorize("hasRole('ADMIN')")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public Developer getById(@PathVariable Long id) {
        log.info("get developer {}", id);
        return devService.getById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('developer:write')")
    public Developer create(@PathVariable Developer developer) {
        log.info("add developer {}", developer.toString());
        devService.add(developer);
        return developer;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('developer:write')")
    public void deleteById(@PathVariable Long id) {
        getAll().removeIf(dev -> dev.getId().equals(id));
    }

}
