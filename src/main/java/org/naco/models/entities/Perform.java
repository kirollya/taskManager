package org.naco.models.entities;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;

@Entity
@Table(name = "perform", uniqueConstraints = @UniqueConstraint(columnNames = {"employee_id", "task_id"}))
public class Perform {

    @Id
    @GeneratedValue
    Long id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    Employee worker;

    @ManyToOne
    @JoinColumn(name = "task_id")
    Task task;

    public Long getId() {
        return id;
    }

    @Nonnull
    public Employee getWorker() {
        return worker;
    }

    public void setWorker(@Nonnull Employee worker) {
        this.worker = worker;
    }

    @Nonnull
    public Task getTask() {
        return task;
    }

    public void setTask(@Nonnull Task task) {
        this.task = task;
    }
}
