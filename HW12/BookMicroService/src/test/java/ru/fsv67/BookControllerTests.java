package ru.fsv67;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.fsv67.repositories.BookRepository;

import java.util.List;
import java.util.Objects;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureWebTestClient
public class BookControllerTests {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    void clearBD() {
        bookRepository.deleteAll();
    }

    @Test
    void getBookByIdTest() {
        Book book = bookRepository.save(new Book("Book test"));

        JUnitBook responseBook = webTestClient.get()
                .uri("/book/" + book.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(JUnitBook.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(responseBook);
        Assertions.assertEquals(book.getId(), responseBook.getId());
        Assertions.assertEquals(book.getTitle(), responseBook.getTitle());
    }

    @Test
    void getBookListTest() {
        List<Book> bookList = bookRepository.saveAll(List.of(
                new Book("Book 1"),
                new Book("Book 2")
        ));

        List<JUnitBook> responseBookList = webTestClient.get()
                .uri("/book")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<JUnitBook>>() {
                })
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(responseBookList);
        Assertions.assertEquals(bookList.size(), responseBookList.size());
        for (JUnitBook book : responseBookList) {
            boolean found = bookList.stream()
                    .filter(it -> Objects.equals(it.getId(), book.getId()))
                    .anyMatch(it -> Objects.equals(it.getTitle(), book.getTitle()));
            Assertions.assertTrue(found);
        }
    }

    @Test
    void setBookTest() {
        Book bookRequest = new Book("Book test");
        JUnitBook responseBook = webTestClient.post()
                .uri("/book")
                .bodyValue(bookRequest)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(JUnitBook.class)
                .returnResult()
                .getResponseBody();

        System.out.println(responseBook);

        Assertions.assertNotNull(responseBook);
        Assertions.assertEquals(bookRequest.getTitle(), responseBook.getTitle());
        Assertions.assertNotNull(responseBook);
    }

    @Test
    void deleteBookTest() {
        Book book = bookRepository.save(new Book("Book test"));

        JUnitBook responseBook = webTestClient.delete()
                .uri("/book/" + book.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(JUnitBook.class)
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(responseBook);
        Assertions.assertEquals(book.getId(), responseBook.getId());
        Assertions.assertEquals(book.getTitle(), responseBook.getTitle());

        Long maxId = jdbcTemplate.queryForObject("SELECT max(id) FROM book", Long.class);
        Assertions.assertNull(maxId);
    }
}
