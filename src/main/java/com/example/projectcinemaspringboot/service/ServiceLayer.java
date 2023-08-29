package com.example.projectcinemaspringboot.service;

import java.util.List;

public interface ServiceLayer<T> {

    void save(T t);

    T findById(Long id);

    List<T> findAll();

    T update(Long id, T t);

    void delete(Long id);

    T findByName(String name);
}
