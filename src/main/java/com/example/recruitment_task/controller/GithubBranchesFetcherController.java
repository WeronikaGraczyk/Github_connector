package com.example.recruitment_task.controller;

import com.example.recruitment_task.service.GithubBranchesFetcherService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@AllArgsConstructor
public class GithubBranchesFetcherController {
    @Autowired
    private final GithubBranchesFetcherService githubBranchesFetcherService;

    private static final String WRONG_HEADER_ERROR_MESSAGE = "Unsupported media type";
    private static final String USER_DOESNT_EXIST_ERROR_MESSAGE = "User doesnt exist";

    @GetMapping(value = "/repositories", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getUserGithubInformation(@RequestParam String username,
                                                      @RequestHeader String Accept) {

        if (MediaType.APPLICATION_JSON_VALUE.equals(Accept)) {
            try {
                return ResponseEntity.ok(githubBranchesFetcherService.fetchUserGithubInformation(username));
            } catch (HttpClientErrorException e) {
                MessageErrorResponse errorResponse = new MessageErrorResponse(HttpStatus.NOT_FOUND.value(), USER_DOESNT_EXIST_ERROR_MESSAGE);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
        } else if (MediaType.APPLICATION_XML_VALUE.equals(Accept)) {
            MessageErrorResponse errorResponse = new MessageErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), WRONG_HEADER_ERROR_MESSAGE);
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).contentType(MediaType.APPLICATION_JSON).body(errorResponse);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
