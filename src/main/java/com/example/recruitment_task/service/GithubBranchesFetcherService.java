package com.example.recruitment_task.service;

import com.example.recruitment_task.entity.BranchInformation;
import com.example.recruitment_task.entity.RepositoryInformation;
import com.example.recruitment_task.entity.RepositoryInformationWithFork;
import com.example.recruitment_task.entity.UserGithubInformation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class GithubBranchesFetcherService {
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${github.access.token}")
    private String token;

    @Value("${github.api.url}")
    private String githubAPIUrl;

    public UserGithubInformation fetchUserGithubInformation(String userName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        ResponseEntity<RepositoryInformationWithFork[]> response = restTemplate.exchange(
                githubAPIUrl + "/users/{userName}/repos",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                RepositoryInformationWithFork[].class, userName);
        RepositoryInformationWithFork[] repositoriesInfoWithFork = response.getBody();

        List<RepositoryInformation> repositoriesWithoutFork = new ArrayList<>();
        if (repositoriesInfoWithFork != null) {
            for (RepositoryInformationWithFork repoInfo : repositoriesInfoWithFork) {
                if (!repoInfo.isFork()) {
                    repositoriesWithoutFork.add(new RepositoryInformation(repoInfo.getName(),userName, getBranchesListFromRepository(repoInfo.getName(), userName)));
                }
            }
        }
        return new UserGithubInformation(repositoriesWithoutFork);
    }

    private List<BranchInformation> getBranchesListFromRepository(String repositoryName, String userName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        ResponseEntity<BranchInformation[]> response = restTemplate.exchange(
                githubAPIUrl + "/repos/{userName}/{repositoryName}/branches",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                BranchInformation[].class, userName, repositoryName);
        BranchInformation[] branchInformations = response.getBody();

        List<BranchInformation> branchInformationsResult = new ArrayList<>();
        if (branchInformations != null) {
            for (BranchInformation branchInfo : branchInformations) {
                ResponseEntity<BranchInformation> responseCommitSha = restTemplate.exchange(
                        githubAPIUrl + "/repos/{userName}/{repositoryName}/commits/{branchName}",
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        BranchInformation.class, userName, repositoryName, branchInfo.getName());

                branchInformationsResult.add(new BranchInformation(branchInfo.getName(),
                        Objects.requireNonNull(responseCommitSha.getBody()).getSha()));
            }
        }
        return branchInformationsResult;
    }
}