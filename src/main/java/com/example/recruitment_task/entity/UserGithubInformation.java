package com.example.recruitment_task.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class UserGithubInformation {
    private String userName;
    private List<RepositoryInformation> repositories;
}
