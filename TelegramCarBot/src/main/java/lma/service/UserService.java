package lma.service;

import lma.entity.User;
import lma.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public User findById(Long id) {
        return userRepository.findById(id);
    }

    public User findByIdWithModelsInitialized(Long id) {
        return userRepository.findByIdWithModelsInitialized(id);
    }

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public void saveUserOrUpdateChatId(Long chatId, Long userId) {
        User user = findById(userId);
        if (user == null) {
            user = save(
                    User.builder()
                            .id(userId)
                            .chatId(chatId)
                            .build()
            );
        }
        user.setChatId(chatId);
    }
}
