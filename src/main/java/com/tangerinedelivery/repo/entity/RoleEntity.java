<<<<<<<< HEAD:src/main/java/com/tangerinedelivery/repo/entity/RoleEntity.java
package com.tangerinedelivery.repo.entity;
========
package com.tangerinedelivery.repos.entities;
>>>>>>>> origin/develop:src/main/java/com/tangerinedelivery/repos/entities/RoleEntity.java

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 60)
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
