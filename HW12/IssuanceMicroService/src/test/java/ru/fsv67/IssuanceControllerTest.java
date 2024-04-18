package ru.fsv67;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.fsv67.repositories.IssuanceRepository;
import ru.fsv67.services.IssuanceService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWebTestClient
public class IssuanceControllerTest {
    @MockBean
    private IssuanceService issuanceServiceMoc;
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private IssuanceRepository issuanceRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void clearBD() {
        issuanceRepository.deleteAll();
    }

    @Test
    void getIssuanceListTest() {
        List<IssuanceTransform> issuanceList = List.of(
                new IssuanceTransform(
                        10L,
                        new Book(1L, "Book1"),
                        new Reader(1L, "Name 1", "Surname 1"),
                        LocalDateTime.now(),
                        null
                ),
                new IssuanceTransform(
                        20L,
                        new Book(2L, "Book2"),
                        new Reader(2L, "Name 2", "Surname 2"),
                        LocalDateTime.now(),
                        null
                ),
                new IssuanceTransform(
                        30L,
                        new Book(3L, "Book3"),
                        new Reader(3L, "Name 3", "Surname 3"),
                        LocalDateTime.now(),
                        null
                )
        );
        List<Issuance> issuances = new ArrayList<>();
        for (IssuanceTransform issuance : issuanceList) {
            issuances.add(
                    new Issuance(
                            issuance.getId(),
                            issuance.getBook().getId(),
                            issuance.getReader().getId(),
                            issuance.getIssuance_at(),
                            issuance.getReturned_at()
                    )
            );
        }
        issuanceRepository.saveAll(issuances);

        List<Issuance> returnIssuanceList = issuanceRepository.findAll();

        Mockito.when(issuanceServiceMoc.getIssuanceList()).thenReturn(issuanceList);

        List<IssuanceTransform> responseBody = webTestClient.get().uri("/issuance")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<IssuanceTransform>>() {
                })
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(returnIssuanceList.size(), responseBody.size());
        for (int i = 0; i < returnIssuanceList.size(); i++) {
            Assertions.assertEquals(returnIssuanceList.get(i).getBookId(), responseBody.get(i).getBook().getId());
            Assertions.assertEquals(returnIssuanceList.get(i).getReaderId(), responseBody.get(i).getReader().getId());
            Assertions.assertEquals(
                    returnIssuanceList.get(i).getIssuance_at().toLocalDate(),
                    responseBody.get(i).getIssuance_at().toLocalDate()
            );
            Assertions.assertEquals(returnIssuanceList.get(i).getReturned_at(), responseBody.get(i).getReturned_at());
        }
        log.info("Test completed");
    }

    @Test
    void getIssuanceByIdTest() {
        IssuanceTransform issuanceTransform = new IssuanceTransform(
                1L,
                new Book(1L, "Book1"),
                new Reader(1L, "Name 1", "Surname 1"),
                LocalDateTime.now(),
                null
        );
        issuanceRepository.save(new Issuance().fromIssuanceTransform(issuanceTransform));

        Mockito.when(issuanceServiceMoc.getIssuanceById(1L)).thenReturn(issuanceTransform);

        IssuanceTransform responseBody = webTestClient.get().uri("issuance/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(IssuanceTransform.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(issuanceTransform.getId(), responseBody.getId());
        Assertions.assertEquals(issuanceTransform.getIssuance_at(), responseBody.getIssuance_at());
        Assertions.assertEquals(issuanceTransform.getReader(), responseBody.getReader());
        Assertions.assertEquals(issuanceTransform.getBook(), responseBody.getBook());
        Assertions.assertEquals(issuanceTransform.getReturned_at(), responseBody.getReturned_at());

        log.info("Test completed");
    }

    @Test
    void returnIssuanceTest() {
        IssuanceTransform issuanceTransform = new IssuanceTransform(
                1L,
                new Book(1L, "Book1"),
                new Reader(1L, "Name 1", "Surname 1"),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        issuanceRepository.save(new Issuance().fromIssuanceTransform(issuanceTransform));

        Mockito.when(issuanceServiceMoc.getIssuanceById(1L)).thenReturn(issuanceTransform);

        IssuanceTransform responseIssuance = webTestClient.put()
                .uri("/issuance/" + issuanceTransform.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(IssuanceTransform.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(responseIssuance);
        Assertions.assertEquals(issuanceTransform.getId(), responseIssuance.getId());
        Assertions.assertEquals(issuanceTransform.getBook(), responseIssuance.getBook());
        Assertions.assertEquals(issuanceTransform.getReader(), responseIssuance.getReader());
        Assertions.assertEquals(issuanceTransform.getIssuance_at(), responseIssuance.getIssuance_at());
        Assertions.assertEquals(issuanceTransform.getReturned_at(), responseIssuance.getReturned_at());

        log.info("Test completed");
    }
}
