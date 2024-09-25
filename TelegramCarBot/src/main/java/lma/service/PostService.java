package lma.service;

import feign.Response;
import lma.client.ApiAvByClient;
import lma.entity.Post;
import lma.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    private final ApiAvByClient apiAvByClient;

    public List<Post> findAllByModelId(Long id) {
        return postRepository.findAllByModel_Id(id);
    }

    public List<Post> findAll(){
        return postRepository.findAll();
    }

    public Page<Post> findAll(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Transactional
    public void delete(Post post) {
        postRepository.delete(post);
    }

    @Transactional
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Transactional
    public void removePostIfOutdated(Post post){
        Response postInfo = apiAvByClient.getPostInfo(post.getId());
        int status = postInfo.status();

        if (HttpStatus.resolve(status).is4xxClientError()) {
            delete(post);
        }
    }
}
