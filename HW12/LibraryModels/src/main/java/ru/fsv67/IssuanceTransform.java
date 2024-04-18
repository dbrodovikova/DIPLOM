package ru.fsv67;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssuanceTransform {
    private Long id;
    private Book book;
    private Reader reader;
    private LocalDateTime issuance_at;
    private LocalDateTime returned_at;

    public IssuanceTransform(Long id, Book book, Reader reader) {
        this.id = id;
        this.book = book;
        this.reader = reader;
    }
}
