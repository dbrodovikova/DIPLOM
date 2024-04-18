package ru.fsv67.services;

import com.github.javafaker.Faker;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fsv67.Book;
import ru.fsv67.repositories.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Класс описывает логику взаимодействия пользователя с хранилищем книг
 */
@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    /**
     * Первоначальные тестовые данные
     */
    @PostConstruct
    void generateData() {
        Faker faker = new Faker();
        List<Book> bookList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            bookList.add(new Book(faker.book().title()));
        }
        bookRepository.saveAll(bookList);
    }

    /**
     * Метод проверки информации о книге
     *
     * @param id идентификатор книги
     * @return если данные не пусты, то метод возвращает книгу по идентификатору, иначе исключение
     */
    public Book getBookById(long id) {
        return bookRepository.findById(id)
                .orElseThrow(
                        () -> new NoSuchElementException("Книга с ID = " + id + " не найдена")
                );
    }

    /**
     * Метод обрабатывает получение списка книг
     *
     * @return если список не пуст, то метод возвращает список книг, иначе исключение
     */
    public List<Book> getBooksList() {
        List<Book> books = bookRepository.findAll();
        if (books.isEmpty()) {
            throw new NoSuchElementException("Список книг в библиотеке пуст");
        }
        return books;
    }

    /**
     * Метод обрабатывает данные введенные пользователем для записи
     *
     * @param book данные о книге, введенные пользователем
     * @return информацию о книге подлежащие записи
     */
    public Book addNewBook(Book book) {
        if (book.getTitle().isEmpty()) {
            throw new RuntimeException("Название книги не задано");
        }
        return bookRepository.save(book);
    }

    /**
     * Метод проверяет информацию перед удалением книги
     *
     * @param id идентификатор книги подлежащей удалению
     * @return описание удаленной книги
     */
    public Book deleteBookById(long id) {
        Book book = getBookById(id);
        bookRepository.deleteById(id);
        if (Objects.isNull(book)) {
            throw new NoSuchElementException("Книга с ID = " + id + " не найдена");
        }
        return book;
    }
}
