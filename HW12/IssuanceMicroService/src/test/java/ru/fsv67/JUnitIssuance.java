package ru.fsv67;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JUnitIssuance {
    private Long id;
    private Long bookId;
    private Long readerId;
    private LocalDateTime issuance_at;
    private LocalDateTime returned_at;
}
