package lma.task1.service;

import lma.task1.constants.CommonConstants;
import lma.task1.dao.UserDao;
import lma.task1.entity.User;
import lma.task1.exception.UserNotFoundException;

import java.util.Optional;

import static lma.task1.constants.CommonConstants.USER_NOT_FOUND_MESSAGE;

public class UserService {
    private static final UserService INSTANCE = new UserService();
    private final UserDao userDao = UserDao.getInstance();

    private UserService() {
    }

    public static UserService getInstance() {
        return INSTANCE;
    }

    public User getUserById(Long id) {
        return userDao.findAll().stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_MESSAGE.formatted(id)));
    }
}
