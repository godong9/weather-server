package com.weather.domain.post;

import com.weather.domain.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * Created by godong9 on 2017. 7. 29..
 */

@Transactional(readOnly = true)
@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    PostLikeRepository postLikeRepository;

    @Autowired
    UserService userService;

    @Transactional(readOnly = false)
    public Post create(PostDto postDto) {
        return postRepository.save(Post.builder()
//                .prediction(predictionService.findOne(postDto.getUserId())) // TODO: 수정 필요
                .user(userService.findOne(postDto.getUserId()))
                .weatherCode(postDto.getWeatherCode())
                .text(postDto.getText())
                .nx(postDto.getNx())
                .ny(postDto.getNy())
                .build());
    }

    public Post findOne(Long postId, Long userId) {
        Post post = postRepository.findOne(postId);

        PostLike postLike = postLikeRepository.findByPostIdAndUserId(postId, userId);
        if (!Objects.isNull(postLike)) {
            post.setIsLiked(true);
        } else {
            post.setIsLiked(false);
        }

        return post;
    }

    public List<Post> findByNxAndNy(Integer nx, Integer ny) {
        return postRepository.findByNxAndNy(nx, ny);
    }
}
