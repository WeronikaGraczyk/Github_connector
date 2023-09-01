package com.example.recruitment_task.entity;

import java.util.List;

import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RepositoryInformationWithFork {
    private String name;
    private List<BranchInformation> branches;
    private boolean isFork;
}