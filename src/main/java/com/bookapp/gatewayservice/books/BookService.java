package com.bookapp.gatewayservice.books;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class BookService {

    public static final String TARGET_SERVICE = "book-catalogue-service";
    private WebClient client;
    //    private final WebClient client = WebClient.create("http://localhost:8080");
    final DiscoveryClient discoveryClient;

    public BookService(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
        createClient();
    }

    public List<Book> findAll() {

        return client.get()
                .uri("/books")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Book>>() {
                })
                .block();
    }

    public Book findById(Long id) {
        return client.get()
                .uri("/books/" + id)
                .retrieve()
                .bodyToMono(Book.class)
                .block();
    }

    public Book save(Book book) {
        return client.post()
                .uri("/books")
                .body(Mono.just(book), Book.class)
                .retrieve()
                .bodyToMono(Book.class)
                .block();
    }


    public void delete(Long id) {
        client.delete()
                .uri("/books/" + id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    private void createClient() {
        var serviceInstanceList = discoveryClient.getInstances(TARGET_SERVICE);
        String clientURI = serviceInstanceList.get(0).getUri().toString();
        client = WebClient.create(clientURI);
    }
}
