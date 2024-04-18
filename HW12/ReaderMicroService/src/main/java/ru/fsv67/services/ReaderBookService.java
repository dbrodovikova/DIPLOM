package ru.fsv67.services;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.fsv67.Book;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class ReaderBookService {
    private final WebClient webClient = WebClient.builder().build();
    private final EurekaClient eurekaClient;

    /**
     * Метод получение книги по ID через API
     *
     * @param id - идентификатор книги
     * @return Описание книги
     */
    public Book getBookByIdInApi(Long id) {
        try {
            return webClient.get()
                    .uri(getBookServiceIp() + "/book/" + id)
                    .retrieve()
                    .bodyToMono(Book.class)
                    .block();
        } catch (Exception e) {
            throw new RuntimeException("Соединение с сервером книг не установлено");
        }

    }

    private String getBookServiceIp() {
        Application application = eurekaClient.getApplication("BOOK-SERVICE");
        List<InstanceInfo> instanceInfoList = application.getInstances();
        int indexInstance = ThreadLocalRandom.current().nextInt(instanceInfoList.size());
        InstanceInfo randomInstanceInfo = instanceInfoList.get(indexInstance);
        return randomInstanceInfo.getHomePageUrl();
//        return "http://" + randomInstanceInfo.getIPAddr() + ":" + randomInstanceInfo.getPort();
    }
}
