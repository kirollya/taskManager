package org.naco.controllers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.naco.models.entities.Perform;
import org.naco.models.entities.Task;
import org.naco.models.repositories.PerformRepository;

@ApplicationScoped
public class PerformController {

    @Inject
    PerformRepository performRepository;

    @Transactional
    public void addPerform(Perform perform) {
        performRepository.persistAndFlush(perform);
    }

    public PerformRepository getPerformRepository() {
        return performRepository;
    }

    @Transactional
    public void deleteAllWithTask(Task task) {
        performRepository.delete("task", task);
    }

}
