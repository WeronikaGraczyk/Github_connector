package com.example.recruitment_task.entity;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RepositoryInformationWithFork {
    private String name;
    private boolean isFork;
}
