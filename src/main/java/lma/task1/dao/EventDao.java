package lma.task1.dao;

import lma.task1.loader.DataLoader;
import lma.task1.entity.Event;

import java.util.List;

public class EventDao {
    private static final EventDao INSTANCE = new EventDao();

    private EventDao() {
    }

    public static EventDao getInstance() {
        return INSTANCE;
    }

    public List<Event> findAll() {
        return DataLoader.getFileData().getEvents();
    }
}
