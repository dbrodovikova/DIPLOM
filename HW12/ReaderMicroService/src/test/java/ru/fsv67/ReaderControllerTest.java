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
import ru.fsv67.repositories.ReaderRepository;
import ru.fsv67.services.ReaderIssuanceService;

import java.util.List;
import java.util.Objects;

@Slf4j
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWebTestClient
public class ReaderControllerTest {
    @MockBean
    private ReaderIssuanceService readerIssuanceService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ReaderRepository readerRepository;
    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    void clearBD() {
        readerRepository.deleteAll();
    }

    @Test
    void getReaderByIdTest() {
        Reader reader = readerRepository.save(new Reader("Reader name 1", "Reader surname 1"));

        JUnitReader responseReader = webTestClient.get()
                .uri("/reader/" + reader.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(JUnitReader.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(responseReader);
        Assertions.assertEquals(reader.getId(), responseReader.getId());
        Assertions.assertEquals(reader.getFirstName(), responseReader.getFirstName());
        Assertions.assertEquals(reader.getLastName(), responseReader.getLastName());
        log.info("Test completed");
    }

    @Test
    void getReaderListTest() {
        List<Reader> readerList = readerRepository.saveAll(List.of(
                new Reader("Reader name 1", "Reader surname 1"),
                new Reader("Reader name 2", "Reader surname 2")
        ));

        List<JUnitReader> responseReaderList = webTestClient.get()
                .uri("/reader")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<JUnitReader>>() {
                })
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(responseReaderList);
        Assertions.assertEquals(readerList.size(), responseReaderList.size());
        for (JUnitReader book : responseReaderList) {
            boolean found = readerList.stream()
                    .filter(it -> Objects.equals(it.getId(), book.getId()))
                    .filter(it -> Objects.equals(it.getFirstName(), book.getFirstName()))
                    .anyMatch(it -> Objects.equals(it.getLastName(), book.getLastName()));
            Assertions.assertTrue(found);
        }
        log.info("Test completed");
    }

    @Test
    void setReaderTest() {
        Reader readerRequest = new Reader("Reader name 1", "Reader surname 1");
        JUnitReader responseReader = webTestClient.post()
                .uri("/reader")
                .bodyValue(readerRequest)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(JUnitReader.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(responseReader);
        Assertions.assertEquals(readerRequest.getFirstName(), responseReader.getFirstName());
        Assertions.assertEquals(readerRequest.getLastName(), responseReader.getLastName());
        log.info("Test completed");
    }

    @Test
    void deleteReaderTest() {
        Reader reader = readerRepository.save(new Reader("Reader name 1", "Reader surname 1"));

        JUnitReader responseReader = webTestClient.delete()
                .uri("/reader/" + reader.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(JUnitReader.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(responseReader);
        Assertions.assertEquals(reader.getId(), responseReader.getId());
        Assertions.assertEquals(reader.getFirstName(), responseReader.getFirstName());
        Assertions.assertEquals(reader.getLastName(), responseReader.getLastName());

        Long maxId = jdbcTemplate.queryForObject("SELECT max(id) FROM reader", Long.class);
        Assertions.assertNull(maxId);
        log.info("Test completed");
    }

    @Test
    void getIssuanceByIdReader() {
        Reader reader = readerRepository.save(new Reader("Name", "Surname"));
        Book book1 = new Book(1L, "Book1");
        Book book2 = new Book(2L, "Book2");

        List<IssuanceTransform> issuanceList = List.of(
                new IssuanceTransform(1L, book1, reader),
                new IssuanceTransform(2L, book2, reader)
        );

        Mockito.when(readerIssuanceService.issuanceListByIdReader(reader.getId())).thenReturn(issuanceList);

        List<IssuanceTransform> responseBody = webTestClient.get()
                .uri("reader/" + reader.getId() + "/issuance")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<IssuanceTransform>>() {
                })
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(issuanceList.size(), responseBody.size());
        for (IssuanceTransform issuance : issuanceList) {
            Assertions.assertEquals(reader.getId(), issuance.getReader().getId());
        }

        log.info("Test completed");
    }
}
