package com.example.projectcinemaspringboot.service.impl;

import com.example.projectcinemaspringboot.exception.EntityNotFoundException;
import com.example.projectcinemaspringboot.model.Session;
import com.example.projectcinemaspringboot.repository.SessionRepository;
import com.example.projectcinemaspringboot.service.ServiceLayer;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SessionService implements ServiceLayer<Session> {

    SessionRepository sessionRepository;

    @Override
    public void save(Session session) {
        sessionRepository.save(session);
    }

    @Override
    public Session findById(Long id) {
        return sessionRepository.findById(id).orElseThrow(()->
                new EntityNotFoundException(String.format("Session with id=%d not found", id)));
    }

    @Override
    public List<Session> findAll() {
        return sessionRepository.findAll();
    }

    @Override
    public Session update(Long id, Session session) {
        Session oldSession = findById(id);
        oldSession.setName(session.getName());
        oldSession.setStart(session.getStart());
        oldSession.setDuration(session.getDuration());
        oldSession.setFinish(session.getFinish());
        oldSession.setImage(session.getImage());
        sessionRepository.save(oldSession);
        return oldSession;
    }

    @Override
    public void delete(Long id) {
        sessionRepository.deleteById(id);
    }

    @Override
    public Session findByName(String name) {
        return sessionRepository.findByName(name);
    }

    public List<Session> findAllId(Long id) {
        return sessionRepository.findAllByMovieId(id);
    }
}
