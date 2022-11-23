package com.nrr.project.resourceserver.entity;

import lombok.*;
import net.bytebuddy.implementation.bind.annotation.IgnoreForBinding;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String todo;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;
}
