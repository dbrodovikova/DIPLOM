package ru.fsv67;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Класс описывает процесс выдачи книги в БД
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Сущность выдачи")
public class Issuance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Идентификатор выдачи")
    private Long id;

    @Column(name = "book_id")
    @Schema(description = "Ссылка идентификатор книги")
    private Long bookId;

    @Column(name = "reader_id")
    @Schema(description = "Ссылка идентификатор читателя")
    private Long readerId;
    /**
     * Дата выдачи книги
     */
    @Column(name = "issuance_at")
    @Schema(description = "Дата и время выдачи книги")
    private LocalDateTime issuance_at;
    /**
     * Дата возврата книги
     */
    @Column(name = "returned_at")
    @Schema(description = "Дата и время возврата книги")
    private LocalDateTime returned_at;

    public Issuance(long bookId, long readerId) {
        this.bookId = bookId;
        this.readerId = readerId;
        this.issuance_at = LocalDateTime.now();
    }

    public Issuance fromIssuanceTransform(IssuanceTransform issuanceTransform) {
        Issuance issuance = new Issuance();
        issuance.setId(issuanceTransform.getId());
        issuance.setBookId(issuanceTransform.getBook().getId());
        issuance.setReaderId(issuanceTransform.getReader().getId());
        issuance.setIssuance_at(issuanceTransform.getIssuance_at());
        issuance.setReturned_at(issuanceTransform.getReturned_at());
        return issuance;
    }
}
