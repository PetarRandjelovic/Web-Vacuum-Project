package rs.raf.demo.services;


import rs.raf.demo.model.User;

import java.util.List;
import java.util.Optional;

public interface IService<T, ID> {
    Optional<User>  findById(ID userId);
    void updateEntity(T userId);
    T createEntity(T user);
    void deleteEntity(ID userId);

    List<T> findAll();
}
