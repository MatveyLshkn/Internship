package lma.service;

import jdk.jfr.Timestamp;
import lma.entity.Model;
import lma.entity.User;
import lma.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<User> findAllBySubscribedModelId(Long modelId){
        return userRepository.findAllBySubscribedModelId(modelId);
    }
}
