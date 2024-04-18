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
import ru.fsv67.aspects_annotation.Time;
import ru.fsv67.services.IssuanceService;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/issuance")
@Tag(name = "Выдача книг", description = "Управление всеми выдачами в системе")
@Time
public class IssuanceController {
    private final IssuanceService issuanceService;

    @Operation(
            summary = "Получение списка выдач",
            description = "Загрузить список всех выдач книг читателям",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешное получение списка выдач", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema =
                            @Schema(implementation = Issuance.class)
                            ))
                    }),
                    @ApiResponse(responseCode = "404", description = "Список выдачи пуст. Книги не выдавались", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))
                    }),
                    @ApiResponse(responseCode = "503", description = "Соединение с сервером не установлено",
                            content = {@Content(mediaType = "*/*", schema = @Schema(implementation = String.class))})
            }
    )
    @GetMapping
    public ResponseEntity<List<IssuanceTransform>> issuanceList() {
        final List<IssuanceTransform> issuanceList;
        try {
            issuanceList = issuanceService.getIssuanceList();
        } catch (NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(issuanceList);
    }

    @Operation(
            summary = "Получить выдачу",
            description = "Загрузить выдачу книги читателю по идентификатору",
            parameters = {
                    @Parameter(name = "id", description = "Идентификатор выдачи")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Успешное получение выдачи книги по " +
                            "идентификатору", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Issuance.class))
                    }),
                    @ApiResponse(responseCode = "404", description = "Выдача отсутствует.", content = {
                            @Content(schema = @Schema(implementation = String.class))
                    })
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<IssuanceTransform> getIssuanceById(@PathVariable long id) {
        IssuanceTransform issuanceTransform;
        try {
            issuanceTransform = issuanceService.getIssuanceById(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(issuanceTransform);
    }

    @Operation(
            summary = "Сохранить выдачу",
            description = "Сохранение факта выдачи книги читателю в системе",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Успешное сохранение выдачи", content = {
                            @Content(mediaType = "application/json", schema =
                            @Schema(implementation = Issuance.class)
                            )
                    }),
                    @ApiResponse(responseCode = "404", description = "Введенные данные не найдены", content =
                            {@Content(mediaType = "*/*", schema = @Schema(implementation = String.class))
                            }),
                    @ApiResponse(responseCode = "405", description = "Сохранение данных не выполнено", content = {
                            @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))
                    }),
                    @ApiResponse(responseCode = "503", description = "Соединение с сервером не установлено",
                            content = {@Content(mediaType = "*/*", schema = @Schema(implementation = String.class))})
            }
    )
    @PostMapping
    public ResponseEntity<Issuance> issuanceBook(@RequestBody IssuanceRequest issuanceRequest) {
        Issuance issuance;
        try {
            issuance = issuanceService.issuanceBook(issuanceRequest);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(issuance);
    }

    @Operation(
            summary = "Изменение выдачи",
            description = "Возврат книги читателем в библиотеку, простановка времени возврата книги",
            parameters = {
                    @Parameter(name = "id", description = "Идентификатор выдачи")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Изменение выдачи выполнено успешно", content = {
                            @Content(mediaType = "application/json", schema =
                            @Schema(implementation = Issuance.class)
                            )
                    }),
                    @ApiResponse(responseCode = "404", description = "Выдача для изменения не найдена",
                            content = {
                                    @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))
                            })
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<IssuanceTransform> returnBookByReader(@PathVariable long id) {
        IssuanceTransform issuanceTransform;
        try {
            issuanceTransform = issuanceService.getIssuanceById(id);
            issuanceService.returnBookByReader(issuanceTransform);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(issuanceTransform);
    }

    @Operation(
            summary = "Получить список выданных книг читателю",
            description = "Получение списка книг по идентификатору читателя",
            parameters = {
                    @Parameter(name = "id", description = "Идентификатор читателя")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Список книг выданных читателю получено успешно",
                            content = {@Content(mediaType = "application/json", schema =
                            @Schema(implementation = Issuance.class))}),
                    @ApiResponse(responseCode = "404", description = "Список пуст. Книги не выдавались",
                            content = {
                                    @Content(mediaType = "*/*", schema = @Schema(implementation = String.class))
                            })
            }
    )
    @GetMapping("/reader/{id}")
    public ResponseEntity<List<IssuanceTransform>> returnIssuanceByIdReader(@PathVariable long id) {
        List<IssuanceTransform> issuanceList;
        try {
            issuanceList = issuanceService.getIssuanceByIdReader(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.OK).body(issuanceList);
    }
}
