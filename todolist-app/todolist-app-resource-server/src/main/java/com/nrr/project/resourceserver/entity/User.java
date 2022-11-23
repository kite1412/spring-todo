package com.nrr.project.resourceserver.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @Column(unique = true)
    private String username;

    @OneToMany(mappedBy = "userId")
    private List<Todo> todos;
}
