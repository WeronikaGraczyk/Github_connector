package com.example.recruitment_task.entity;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RepositoryInformation {
    private String name;
    private List<BranchInformation> branches;
    private boolean isFork;
}
