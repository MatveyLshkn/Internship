package lma.task1.service;

import lma.task1.dao.EventDao;
import lma.task1.entity.Event;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class EventService {
    private static final EventService INSTANCE = new EventService();
    private final EventDao eventDao = EventDao.getInstance();

    private EventService() {
    }

    public static EventService getInstance() {
        return INSTANCE;
    }

    public List<Event> getAll(){
        return eventDao.findAll().stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
