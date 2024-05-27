package org.naco.models.repositories;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.naco.models.entities.Perform;

@ApplicationScoped
public class PerformRepository implements PanacheRepository<Perform> {
}
