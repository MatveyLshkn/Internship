package lma.task1.dao;

import lma.task1.loader.DataLoader;
import lma.task1.entity.User;

import java.util.List;

public class UserDao {
    private static final UserDao INSTANCE = new UserDao();

    private UserDao() {
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }

    public List<User> findAll() {
        return DataLoader.getFileData().getUsers();
    }
}
