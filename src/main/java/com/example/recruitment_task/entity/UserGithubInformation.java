package com.example.recruitment_task.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@AllArgsConstructor
public class UserGithubInformation {
    private String userName;
    private List<RepositoryInformation> repositories;
}
