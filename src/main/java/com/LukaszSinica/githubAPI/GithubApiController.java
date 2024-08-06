package com.LukaszSinica.githubAPI;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class GithubApiController {

	private GithubApiService githubApiService;

	public GithubApiController(GithubApiService githubApiService) {
		super();
		this.githubApiService = githubApiService;
	}

	@GetMapping("/{username}/repos")
	public Mono<List<GithubApiRepository>> listRepositories(@PathVariable String username) {

		return githubApiService.githubRepositoryList(username);
	}
}
