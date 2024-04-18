package ru.fsv67.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.fsv67.Book;
import ru.fsv67.aspects_annotation.Time;
import ru.fsv67.services.BookService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
@Tag(name = "Книги", description = "Управление всеми книгами в системе")
@Time
public class BookController {
    private final BookService bookService;

    @Operation(
            summary = "Получение списка книг",
            description = "Загрузка всех книг сохранённых в системе",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешное получение списка книг", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema =
                            @Schema(implementation = Book.class)
                            ))
                    }),
                    @ApiResponse(responseCode = "404", description = "Список книг пуст. Книги отсутствуют в системе",
                            content = {
                                    @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))
                            })
            }
    )
    @GetMapping()
    public ResponseEntity<List<Book>> getBooksList() {
        final List<Book> books;
        try {
            books = bookService.getBooksList();
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(books);
    }

    @Operation(
            summary = "Получение книги",
            description = "Загружает книгу по идентификатору",
            parameters = {
                    @Parameter(name = "id", description = "Идентификатор книги")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешное получение книги", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)
                            )
                    }),
                    @ApiResponse(responseCode = "404", description = "Книга не найдена.",
                            content = {
                                    @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))
                            })
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable long id) {
        final Book book;
        try {
            book = bookService.getBookById(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(book);
    }


    @Operation(
            summary = "Сохранение книги",
            description = "Сохраняет новую книгу в системе",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Книга сохранена успешно", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)
                            )
                    }),
                    @ApiResponse(responseCode = "405", description = "Сохранение данных не выполнено",
                            content = {
                                    @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))
                            })
            }
    )
    @PostMapping()
    public ResponseEntity<Book> addNewBook(@RequestBody Book newBook) {
        final Book book;
        try {
            book = bookService.addNewBook(newBook);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }


    @Operation(
            summary = "Удаление книги",
            description = "Удаляет книгу из системы по идентификатору",
            parameters = {
                    @Parameter(name = "id", description = "Идентификатор книги")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешное удаление книги", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Book.class)
                            )
                    }),
                    @ApiResponse(responseCode = "404", description = "Книга не найдена. Удаление не возможно",
                            content = {
                                    @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))
                            })
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBookById(@PathVariable long id) {
        final Book book;
        try {
            book = bookService.deleteBookById(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(book);
    }
}
