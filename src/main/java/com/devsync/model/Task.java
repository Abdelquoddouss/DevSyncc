package com.devsync.model;

import com.devsync.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
public class Task {

    @Id
    @GeneratedValue
    private long id;

    private String title;

    private String desctiption;

    private LocalDate creationDate;
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;




}
