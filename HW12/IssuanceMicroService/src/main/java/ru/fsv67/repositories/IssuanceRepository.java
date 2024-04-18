package ru.fsv67.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.fsv67.Issuance;

import java.util.List;

/**
 * Класс описывает выдачу книг читателям
 */
@Repository
public interface IssuanceRepository extends JpaRepository<Issuance, Long> {
    /**
     * Метод найти все выдачи по идентификатору читателя
     *
     * @param id идентификатор читателя
     * @return список выданных книг
     */
    @Query("SELECT I FROM Issuance I WHERE I.readerId = :id and I.returned_at IS NULL")
    List<Issuance> findIssuanceByReaderId(long id);
}
