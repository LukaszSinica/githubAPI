package com.LukaszSinica.githubAPI;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class GithubApiService {

	@Autowired
	private WebClient.Builder webClientBuilder;

	public Mono<List<GithubApiRepository>> githubRepositoryList(String username) {
		String url = String.format("https://api.github.com/users/%s/repos", username);
        return webClientBuilder.build()
                .get()
                .uri(url)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), clientResponse -> handleError(clientResponse))
                .onStatus(status -> status.is5xxServerError(), clientResponse -> handleError(clientResponse))
                .bodyToFlux(GithubApiRepository.class)
                .flatMap(repo -> {
                    String repositoryName = repo.getName();
                    String repositoryOwnerLogin = repo.getOwner().getLogin();
                    return githubBranchesFromRepositoryList(repositoryOwnerLogin, repositoryName)
                            .collectList()
                            .doOnNext(repo::setBranch)
                            .thenReturn(repo);
                })
                .collectList()
                .subscribeOn(Schedulers.boundedElastic())
                ;
	}

	public Flux<Branch> githubBranchesFromRepositoryList(String repositoryName, String repositoryOwnerLogin) {
		String url = String.format("https://api.github.com/repos/%s/%s/branches", repositoryName, repositoryOwnerLogin);

		return webClientBuilder.build()
						       .get()
						       .uri(url)
						       .retrieve()
				               .onStatus(status -> status.is4xxClientError(), clientResponse -> handleError(clientResponse))
				               .onStatus(status -> status.is5xxServerError(), clientResponse -> handleError(clientResponse))
						       .bodyToFlux(Branch.class);
	}

    private Mono<ResponseStatusException> handleError(ClientResponse clientResponse) {
        return clientResponse.bodyToMono(String.class)
                .flatMap(errorBody -> {
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        JsonNode errorJson = mapper.readTree(errorBody);
                        int status = errorJson.path("status").asInt(clientResponse.statusCode().value());
                        String message = errorJson.path("message").asText("Unknown error");
                        return Mono.error(new ResponseStatusException(HttpStatus.valueOf(status), message));
                    } catch (Exception e) {
                        return Mono.error(new ResponseStatusException(clientResponse.statusCode(), "Error processing response"));
                    }
                });
    }
}
