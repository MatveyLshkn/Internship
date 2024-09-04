package lma.mapper;

import lma.dto.PostReadDto;
import lma.entity.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public PostReadDto map(Post post) {
        return new PostReadDto(
                post.getId(),
                post.getModel().getId(),
                post.getInfo(),
                post.getUrl()
        );
    }
}
