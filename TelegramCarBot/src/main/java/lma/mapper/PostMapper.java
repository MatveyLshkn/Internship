package lma.mapper;

import lma.dto.PostDto;
import lma.entity.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public PostDto map(Post post) {
        return new PostDto(
                post.getId(),
                post.getModel().getId(),
                post.getInfo(),
                post.getUrl()
        );
    }
}
