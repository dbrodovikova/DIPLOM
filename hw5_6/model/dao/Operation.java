package hw7.model.dao;

import java.util.List;

public interface Operation<T> {
    List<T> findAll();
    void saveAll(List<T> data);
}
