package org.naco.models.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.naco.models.entities.Task;

@ApplicationScoped
public class TaskRepository implements PanacheRepository<Task> {
}
