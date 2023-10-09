package hw7.model.repository;

import hw7.model.Note;

public interface Repository<T> {
    void create(T t);
    T read(long id);
    void update(long id, T t);
    boolean delete(Long id);

}
