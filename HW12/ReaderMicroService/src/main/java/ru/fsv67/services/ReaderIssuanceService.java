package ru.fsv67.services;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import ru.fsv67.Issuance;
import ru.fsv67.IssuanceTransform;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class ReaderIssuanceService {
    private final WebClient webClient = WebClient.builder().build();
    private final EurekaClient eurekaClient;
    private final ReaderBookService readerBookService;
    private final ReaderService readerService;

    /**
     * Метод получения адреса зарегистрированного сервиса
     *
     * @return ip адрес и port
     */
    private String getIssuanceServiceIp() {
        Application application = eurekaClient.getApplication("ISSUANCE-SERVICE");
        List<InstanceInfo> instanceInfoList = application.getInstances();
        int indexInstance = ThreadLocalRandom.current().nextInt(instanceInfoList.size());
        InstanceInfo randomInstanceInfo = instanceInfoList.get(indexInstance);
        return randomInstanceInfo.getHomePageUrl();
//        return "http://" + randomInstanceInfo.getIPAddr() + ":" + randomInstanceInfo.getPort();
    }

    /**
     * Метод обрабатывает получение списка выдачи книг читателю по ID
     *
     * @param id идентификатор читателя
     * @return если список не пуст, то метод возвращает список выдачи книг, иначе исключение
     */
    public List<IssuanceTransform> issuanceListByIdReader(long id) {
        List<IssuanceTransform> list;
        try {
            list = webClient.get()
                    .uri(getIssuanceServiceIp() + "/issuance/reader/" + id)
                    .retrieve()
                    .bodyToFlux(IssuanceTransform.class)
                    .collectList()
                    .block();
        } catch (WebClientResponseException e) {
            throw new NoSuchElementException("Читателю с ID = " + id + " книги не выдавались");
        } catch (Exception e) {
            throw new RuntimeException("Соединение с сервером выдачи книг не установлено");
        }
        return list;
    }

    /**
     * Метод преобразования стандартной выдачи в выдачу с полным описанием
     *
     * @param issuance стандартная выдача
     * @return Выдача с полным описанием
     */
    public IssuanceTransform createIssuanceDTO(Issuance issuance) {
        IssuanceTransform issuanceTransform = new IssuanceTransform();
        issuanceTransform.setId(issuance.getId());
        issuanceTransform.setBook(readerBookService.getBookByIdInApi(issuance.getBookId()));
        issuanceTransform.setReader(readerService.getReaderById(issuance.getReaderId()));
        issuanceTransform.setIssuance_at(issuance.getIssuance_at());
        issuanceTransform.setReturned_at(issuance.getReturned_at());
        return issuanceTransform;
    }
}
