package com.example.recruitment_task.service;

import com.example.recruitment_task.entity.BranchInformation;
import com.example.recruitment_task.entity.RepositoryInformation;
import com.example.recruitment_task.entity.UserGithubInformation;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
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
@NoArgsConstructor
public class GithubBranchesFetcherService {
    private static final String GITHUB_API_URL = "https://api.github.com/";
    private RestTemplate restTemplate = new RestTemplate();
    @Value("${github}")
    private String authToken;


    public UserGithubInformation fetchUserGithubInformation(String userName) {
        System.out.println(authToken);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        ResponseEntity<RepositoryInformation[]> response = restTemplate.exchange(
                GITHUB_API_URL + "/users/{userName}/repos",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                RepositoryInformation[].class, userName);
        RepositoryInformation[] repositories = response.getBody();
        List<RepositoryInformation> withoutForkRespositories = new ArrayList<>();
        for (RepositoryInformation repoInfo : repositories) {
            if (!repoInfo.isFork()) {
                repoInfo.setBranches(getBranchesListFromRepository(repoInfo.getName(), userName, authToken));
                withoutForkRespositories.add(repoInfo);
            }
        }
        System.out.println(withoutForkRespositories.size());
//        Arrays.stream(new List[]{withoutForkRespositories})
//                .forEach(repoInfo -> System.out.println(repoInfo));
        return new UserGithubInformation(userName, withoutForkRespositories);
    }

    private List<BranchInformation> getBranchesListFromRepository(String repositoryName, String userName, String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);

        ResponseEntity<BranchInformation[]> response = restTemplate.exchange(
                GITHUB_API_URL + "/repos/{userName}/{repositoryName}/branches",
                HttpMethod.GET,
                new HttpEntity<>(headers),
                BranchInformation[].class, userName, repositoryName);
        BranchInformation[] branchInformations = response.getBody();

        List<BranchInformation> branchInformationsResult = new ArrayList<>();
        for (BranchInformation branchInfo : branchInformations) {
            ResponseEntity<BranchInformation> responseCommitSha = restTemplate.exchange(
                    GITHUB_API_URL + "/repos/{userName}/{repositoryName}/commits/{branchName}",
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    BranchInformation.class, userName, repositoryName, branchInfo.getName());

            branchInformationsResult.add(new BranchInformation(branchInfo.getName(),
                    Objects.requireNonNull(responseCommitSha.getBody()).getSha()));
        }
        return branchInformationsResult;
    }
}
