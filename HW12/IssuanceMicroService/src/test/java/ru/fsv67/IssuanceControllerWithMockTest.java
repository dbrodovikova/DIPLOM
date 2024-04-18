package ru.fsv67;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.fsv67.repositories.IssuanceRepository;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class IssuanceControllerWithMockTest {
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private IssuanceRepository issuanceRepository;

    @BeforeEach
    void clearBD() {
        issuanceRepository.deleteAll();
    }

    @Test
    void setIssuanceTest() {

        Issuance issuanceRequest = new Issuance(1L, 3L);

        JUnitIssuance responseIssuance = webTestClient.post()
                .uri("/issuance")
                .bodyValue(issuanceRequest)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(JUnitIssuance.class)
                .returnResult()
                .getResponseBody();

        System.out.println(issuanceRequest);
        System.out.println(responseIssuance);

        Assertions.assertNotNull(responseIssuance);
        Assertions.assertEquals(issuanceRequest.getReaderId(), responseIssuance.getReaderId());
        Assertions.assertEquals(issuanceRequest.getBookId(), responseIssuance.getBookId());
        Assertions.assertEquals(issuanceRequest.getReturned_at(), responseIssuance.getReturned_at());
        Assertions.assertEquals(issuanceRequest.getIssuance_at().toLocalDate(), responseIssuance.getIssuance_at().toLocalDate());
        log.info("Test completed");
    }

}
