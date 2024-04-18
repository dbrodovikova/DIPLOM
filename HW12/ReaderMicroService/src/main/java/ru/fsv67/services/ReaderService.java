package ru.fsv67.services;

import com.github.javafaker.Faker;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fsv67.Reader;
import ru.fsv67.repositories.ReaderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReaderService {
    private final ReaderRepository readerRepository;

    /**
     * Первоначальные тестовые данные
     */
    @PostConstruct
    void generateData() {
        Faker faker = new Faker();
        List<Reader> readerList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            readerList.add(new Reader(faker.name().firstName(), faker.name().lastName()));
        }
        readerRepository.saveAll(readerList);
    }

    /**
     * Метод обрабатывает получение читателя по ID
     *
     * @param id идентификатор читателя
     * @return если данные полученные не пусты, то метод возвращает читателя по ID, иначе исключение
     */
    public Reader getReaderById(long id) {
        return readerRepository
                .findById(id)
                .orElseThrow(
                        () -> new NoSuchElementException("Читатель с ID = " + id + " не найден")
                );
    }

    /**
     * Метод обрабатывает получение списка читателей
     *
     * @return если список не пуст, то метод возвращает список читателей, иначе исключение
     */
    public List<Reader> getReaderList() {
        List<Reader> readers = readerRepository.findAll();
        if (readers.isEmpty()) {
            throw new NoSuchElementException("Список читателей пуст");
        }
        return readers;
    }

    /**
     * Метод обрабатывает данные читателя, введенные пользователем, для записи
     *
     * @param reader данные полученные от пользователя
     * @return если данные введенные корректно, то метод возвращает информацию о пользователе подлежащей для записи,
     * иначе исключение
     */
    public Reader addNewReader(Reader reader) {
        if (reader.getFirstName().isEmpty() && reader.getLastName().isEmpty()) {
            throw new RuntimeException("Имя или фамилия читателя не заданы");
        }
        return readerRepository.save(reader);
    }

    /**
     * Метод проверяет информацию о читателе перед удалением
     *
     * @param id идентификатор читателя подлежащего удалению
     * @return информацию удаленного читателя
     */
    public Reader deleteReaderById(long id) {
        Reader reader = getReaderById(id);
        readerRepository.deleteById(id);
        if (Objects.isNull(reader)) {
            throw new NoSuchElementException("Читатель с ID = " + id + " не найден");
        }
        return reader;
    }

}