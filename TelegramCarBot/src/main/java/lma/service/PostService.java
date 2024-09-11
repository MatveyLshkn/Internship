package lma.service;

import com.fasterxml.jackson.databind.JsonNode;
import feign.Response;
import lma.client.ApiAvByClient;
import lma.client.PageAvByClient;
import lma.dto.PostDto;
import lma.entity.Model;
import lma.entity.ModelCheck;
import lma.entity.Post;
import lma.mapper.PostMapper;
import lma.parser.JsonParser;
import lma.parser.PageParser;
import lma.repository.ModelCheckRepository;
import lma.repository.ModelRepository;
import lma.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static lma.constants.CommonConstants.CHECK_FOR_NEW_POSTS_RATE;
import static lma.constants.CommonConstants.OUTDATED_POSTS_CRON_EXPRESSION;
import static lma.constants.CommonConstants.POST_KAFKA_TOPIC_NAME;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;


    public List<Post> findAllByModelId(Long id) {
        return postRepository.findAllByModel_Id(id);
    }

    public Long countPostsByModelId(Long id) {
        return postRepository.countPostByModel_Id(id);
    }
}
