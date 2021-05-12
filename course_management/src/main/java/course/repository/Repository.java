package course.repository;

import java.util.List;

public interface Repository<T, ID>{

    T add(T t);

    T getById(ID id);

    List<T> getAll();

    T update(T t);

    boolean delete(T t);
}
