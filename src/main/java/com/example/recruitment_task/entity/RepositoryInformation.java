package com.example.recruitment_task.entity;

import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RepositoryInformation {
    private String repositoryName;
    private String username;
    private List<BranchInformation> branches;
}
