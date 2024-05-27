package org.naco.models.entities;

import java.util.ArrayList;
import java.util.List;

public enum Post {

    WORKER(0),
    CHIEF(1),
    DIRECTOR(2);

    private Integer id;

    Post(int id) {
        this.id = id;
    }

    public Integer getValue() {
        return id;
    }

    public static List<Post> getAll() {
        List<Post> allPosts = new ArrayList<>();
        allPosts.add(WORKER);
        allPosts.add(CHIEF);
        allPosts.add(DIRECTOR);
        return allPosts;
    }

}
