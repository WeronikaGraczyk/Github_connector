package com.example.recruitment_task.entity;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserGithubInformation {
    private String userName;
    private List<RepositoryInformation> repositories;
}
