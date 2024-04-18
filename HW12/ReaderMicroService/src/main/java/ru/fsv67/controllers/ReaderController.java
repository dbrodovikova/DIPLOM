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
import ru.fsv67.Issuance;
import ru.fsv67.IssuanceTransform;
import ru.fsv67.Reader;
import ru.fsv67.aspects_annotation.Time;
import ru.fsv67.services.ReaderIssuanceService;
import ru.fsv67.services.ReaderService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reader")
@Tag(name = "Читатели", description = "Управление всеми читателями в системе")
@Time
public class ReaderController {
    private final ReaderService readerService;
    private final ReaderIssuanceService readerIssuanceService;

    @Operation(
            summary = "Получение списка читателей",
            description = "Загрузка всех читателей сохранённых в системе",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешное получение списка читателей", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema =
                            @Schema(implementation = Reader.class)
                            ))
                    }),
                    @ApiResponse(responseCode = "404", description = "Список читателей пуст. Читатели отсутствуют в " +
                            "системе", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))
                    })
            }
    )
    @GetMapping()
    public ResponseEntity<List<Reader>> getReaderList() {
        final List<Reader> readers;
        try {
            readers = readerService.getReaderList();
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(readers);
    }

    @Operation(
            summary = "Получение читателя",
            description = "Загрузка читателя по идентификатору",
            parameters = {
                    @Parameter(name = "id", description = "Идентификатор читателя")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешное получение читателя", content = {
                            @Content(mediaType = "application/json", schema =
                            @Schema(implementation = Reader.class)
                            )
                    }),
                    @ApiResponse(responseCode = "404", description = "Читатель отсутствует в системе", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))
                    })
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<Reader> getReaderById(@PathVariable long id) {
        final Reader reader;
        try {
            reader = readerService.getReaderById(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(reader);
    }

    @Operation(
            summary = "Получение выдач читателя",
            description = "Загрузка списка выдач по идентификатору читателя",
            parameters = {
                    @Parameter(name = "id", description = "Идентификатор читателя")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешное получение выдач книг читателю",
                            content = {
                                    @Content(mediaType = "application/json", array = @ArraySchema(schema =
                                    @Schema(implementation = Issuance.class)
                                    ))
                            }),
                    @ApiResponse(responseCode = "404", description = "Выдачи не найдены. Читателю книги не " +
                            "выдавались", content = {@Content(mediaType = "*/*",
                            schema = @Schema(implementation = String.class))
                    }),
                    @ApiResponse(responseCode = "503", description = "Соединение с сервером выдачи книг не установлено",
                            content =
                                    {@Content(mediaType = "*/*",
                                            schema = @Schema(implementation = String.class))
                                    })
            }
    )
    @GetMapping("/{id}/issuance")
    public ResponseEntity<List<IssuanceTransform>> getIssuanceByIdReader(@PathVariable long id) {
        List<IssuanceTransform> issuanceList;
        try {
            issuanceList = readerIssuanceService.issuanceListByIdReader(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(issuanceList);
    }

    @Operation(
            summary = "Сохранение читателя",
            description = "Сохранение читателя в системе",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Успешное сохранение читателя в системе",
                            content = {@Content(mediaType = "application/json", schema =
                            @Schema(implementation = Reader.class)
                            )
                            }),
                    @ApiResponse(responseCode = "405", description = "Сохранение данных не выполнено", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))
                    })
            }
    )
    @PostMapping()
    public ResponseEntity<Reader> addNewReader(@RequestBody Reader newBook) {
        Reader reader;
        try {
            reader = readerService.addNewReader(newBook);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(reader);
    }

    @Operation(
            summary = "Удаление читателя",
            description = "Удаление читателя из системы по идентификатору",
            parameters = {
                    @Parameter(name = "id", description = "Идентификатор читателя")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешное удаление читателя", content = {
                            @Content(mediaType = "application/json", schema =
                            @Schema(implementation = Reader.class)
                            )
                    }),
                    @ApiResponse(responseCode = "404", description = "Читатель отсутствует в системе", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))
                    })
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Reader> deleteReaderById(@PathVariable long id) {
        final Reader reader;
        try {
            reader = readerService.deleteReaderById(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(reader);
    }
}
