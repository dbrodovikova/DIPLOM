package ru.fsv67;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Сущность читатель")
public class Reader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Идентификатор читателя")
    private Long id;

    @Column(name = "firstName")
    @Schema(description = "Поле имя читателя")
    private String firstName;

    @Column(name = "lastName")
    @Schema(description = "Поле фамилия читателя")
    private String lastName;

    public Reader(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
