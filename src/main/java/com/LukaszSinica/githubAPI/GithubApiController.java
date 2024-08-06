package com.LukaszSinica.githubAPI;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class GithubApiController {

	private GithubApiService githubApiService;

	public GithubApiController(GithubApiService githubApiService) {
		super();
		this.githubApiService = githubApiService;
	}

	@GetMapping("/list-repositories")
	public Mono<List<GithubApiRepository>> listRepositories() {

		return githubApiService.githubRepositoryList();
	}
}
