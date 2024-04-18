package ru.fsv67.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import ru.fsv67.Book;
import ru.fsv67.IssuanceTransform;
import ru.fsv67.Reader;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class WebService {
    private final WebClient webClient;

    public WebService(ReactorLoadBalancerExchangeFilterFunction loadBalancerExchangeFilterFunction) {
        this.webClient = WebClient.builder()
                .filter(loadBalancerExchangeFilterFunction)
                .build();
    }

    public List<Book> getBookList() {
        List<Book> bookList = new ArrayList<>();
        try {
            bookList = webClient.get()
                    .uri("http://BOOK-SERVICE/book")
                    .retrieve()
                    .bodyToFlux(Book.class)
                    .collectList()
                    .block();
        } catch (WebClientResponseException e) {
            return new ArrayList<>();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return bookList;
    }

    public List<IssuanceTransform> getIssuanceList() {
        List<IssuanceTransform> issuanceTransforms = new ArrayList<>();
        try {
            issuanceTransforms = webClient.get()
                    .uri("http://ISSUANCE-SERVICE/issuance")
                    .retrieve()
                    .bodyToFlux(IssuanceTransform.class)
                    .collectList()
                    .block();
        } catch (WebClientResponseException e) {
            return new ArrayList<>();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return issuanceTransforms;
    }

    public List<Reader> getReaderList() {
        return webClient.get()
                .uri("http://READER-SERVICE/reader")
                .retrieve()
                .bodyToFlux(Reader.class)
                .collectList()
                .block();
    }

    public Reader getReaderById(Long id) {
        return webClient.get()
                .uri("http://READER-SERVICE/reader/" + id)
                .retrieve()
                .bodyToMono(Reader.class)
                .block();
    }

    public List<IssuanceTransform> getIssuanceByIdReader(Long id) {
        try {
            return webClient.get()
                    .uri("http://READER-SERVICE/reader/" + id + "/issuance")
                    .retrieve()
                    .bodyToFlux(IssuanceTransform.class)
                    .collectList()
                    .block();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
