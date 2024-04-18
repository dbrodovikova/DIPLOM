package ru.fsv67.services;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import ru.fsv67.*;
import ru.fsv67.controllers.IssuanceRequest;
import ru.fsv67.repositories.IssuanceRepository;

import java.time.LocalDateTime;
import java.util.*;

@Service
//@RequiredArgsConstructor
@EnableConfigurationProperties(ReaderProperties.class)
public class IssuanceService {
    private final IssuanceRepository issuanceRepository;
    private final ReaderProperties maxIssuedBooks;
    private final WebClient webClient;


    public IssuanceService(IssuanceRepository issuanceRepository, ReaderProperties maxIssuedBooks,
                           ReactorLoadBalancerExchangeFilterFunction loadBalancerExchangeFilterFunction) {
        this.issuanceRepository = issuanceRepository;
        this.maxIssuedBooks = maxIssuedBooks;
        this.webClient = WebClient.builder()
                .filter(loadBalancerExchangeFilterFunction)
                .build();
    }


    /**
     * Первоначальные тестовые данные
     */
    @PostConstruct
    void generateData() {
        Random random = new Random();
        List<Issuance> issuanceList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            long idReader = random.nextLong(10) + 1;
            if (issuanceList.stream().filter(it -> it.getReaderId().equals(idReader)).count() != maxIssuedBooks.getMaxAllowedBooks()) {
                issuanceList.add(new Issuance(random.nextLong(20) + 1, idReader));
            } else {
                i--;
            }
        }
        issuanceRepository.saveAll(issuanceList);
        returnBookByReader(getIssuanceById(random.nextLong(20) + 1));
        returnBookByReader(getIssuanceById(random.nextLong(20) + 1));
        returnBookByReader(getIssuanceById(random.nextLong(20) + 1));
    }

    /**
     * Метод проверяет получение списка всех выдач книг
     *
     * @return если список не пуст, то метод возвращает список всех выдач книг, иначе исключение
     */
    public List<IssuanceTransform> getIssuanceList() {
        List<Issuance> issuanceList = issuanceRepository.findAll();
        if (issuanceList.isEmpty()) {
            throw new NullPointerException("Книги не кому не выдавались");
        }
        return getIssuanceTransforms(issuanceList);
    }

    private List<IssuanceTransform> getIssuanceTransforms(List<Issuance> issuanceList) {
        List<IssuanceTransform> issuanceTransformList = new ArrayList<>();
        try {
            for (Issuance issuance : issuanceList) {
                issuanceTransformList.add(createIssuanceDTO(issuance));
            }
        } catch (Exception e) {
            throw new RuntimeException("Соединение с сервером не установлено");
        }
        return issuanceTransformList;
    }

    /**
     * Метод обработки получения выдачи по ID
     *
     * @param id идентификатор выдачи
     * @return если выдач с ID найдена, то метод выведет выдачу, иначе исключение
     */
    public IssuanceTransform getIssuanceById(Long id) {
        Issuance issuance = issuanceRepository
                .findById(id)
                .orElseThrow(
                        () -> new NoSuchElementException("Выдача с ID = " + id + " не найдена")
                );
        IssuanceTransform issuanceTransform;
        try {
            issuanceTransform = createIssuanceDTO(issuance);
        } catch (Exception e) {
            throw new RuntimeException("Соединение с сервером не установлено");
        }
        return issuanceTransform;
    }

    /**
     * Метод поиска выдачи книг читателю по ID
     *
     * @param id идентификатор читателя
     * @return список всех выдач книг читателю c ID
     */
    public List<IssuanceTransform> getIssuanceByIdReader(Long id) {
        List<Issuance> issuanceList = issuanceRepository.findIssuanceByReaderId(id);
        if (issuanceList.isEmpty()) {
            throw new NoSuchElementException("Читателю с ID = " + id + " книги не выдавались");
        }
        return getIssuanceTransforms(issuanceList);
    }

    /**
     * Метод обрабатывает введенные данные пользователем
     * при выдаче книг читателю
     *
     * @param issuanceRequest данные введенные пользователем
     * @return если данные введенные пользователем корректны, то метод вернет информацию о выдаче книги читателю,
     * иначе исключение
     */
    public Issuance issuanceBook(IssuanceRequest issuanceRequest) {
//        try {
//            getBookByIdInApi(issuanceRequest.getBookId());
//        } catch (WebClientResponseException e) {
//            throw new NoSuchElementException("Не найдена книга с ID = " + issuanceRequest.getBookId());
//        } catch (Exception e) {
//            throw new RuntimeException("Соединение с сервером книг не установлено");
//        }
//
//        try {
//            getReaderByIdInApi(issuanceRequest.getReaderId());
//        } catch (WebClientResponseException e) {
//            throw new NoSuchElementException("Не найден читатель с ID = " + issuanceRequest.getReaderId());
//        } catch (Exception e) {
//            throw new RuntimeException("Соединение с сервером читатели не установлено");
//        }
//
//        if (getIssuanceByIdReader(issuanceRequest.getReaderId()).size() >= maxIssuedBooks.getMaxAllowedBooks()) {
//            throw new IllegalStateException(
//                    "Читатель с ID = " + issuanceRequest.getReaderId() + " превысил лимит книг в одни руки"
//            );
//        }
        Issuance issuance = new Issuance(issuanceRequest.getBookId(), issuanceRequest.getReaderId());
        issuanceRepository.save(issuance);
        return issuance;
    }

    /**
     * Метод проставляет дату возврата книги читателем, тем самым закрывает выдачу
     */
    public void returnBookByReader(IssuanceTransform issuanceTransform) {
        if (!Objects.isNull(issuanceTransform.getReturned_at())) {
            throw new NoSuchElementException("Выдача с ID = " + issuanceTransform.getId() + " закрыта");
        }
        issuanceTransform.setReturned_at(LocalDateTime.now());
        issuanceRepository.save(new Issuance().fromIssuanceTransform(issuanceTransform));
    }

    /**
     * Метод получение книги по ID через API
     *
     * @param id - идентификатор книги
     * @return Описание книги
     */
    private Book getBookByIdInApi(Long id) {
        return webClient.get()
                .uri("http://BOOK-SERVICE/book/" + id)
                .retrieve()
                .bodyToMono(Book.class)
                .block();
    }

    /**
     * Метод получение описание читателя по ID через API
     *
     * @param id - идентификатор читателя
     * @return описание читателя
     */
    private Reader getReaderByIdInApi(Long id) {
        return webClient.get()
                .uri("http://READER-SERVICE/reader/" + id)
                .retrieve()
                .bodyToMono(Reader.class)
                .block();
    }

    /**
     * Метод преобразования стандартной выдачи в выдачу с полным описанием
     *
     * @param issuance стандартная выдача
     * @return Выдача с полным описанием
     */
    public IssuanceTransform createIssuanceDTO(Issuance issuance) throws WebClientResponseException {
        IssuanceTransform issuanceTransform = new IssuanceTransform();
        issuanceTransform.setId(issuance.getId());
        issuanceTransform.setBook(getBookByIdInApi(issuance.getBookId()));
        issuanceTransform.setReader(getReaderByIdInApi(issuance.getReaderId()));
        issuanceTransform.setIssuance_at(issuance.getIssuance_at());
        issuanceTransform.setReturned_at(issuance.getReturned_at());
        return issuanceTransform;
    }
}
