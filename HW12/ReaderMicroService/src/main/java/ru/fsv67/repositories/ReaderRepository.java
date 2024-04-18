package ru.fsv67.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fsv67.Reader;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, Long> {
}
