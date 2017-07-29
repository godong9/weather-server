package com.weather.domain.post;

import com.weather.domain.prediction.WeatherCode;
import com.weather.domain.user.UserService;
import com.weather.exception.PostException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Random;

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
        WeatherCode code = null;

        Random randomGenerator = new Random();
        int codeIdx = randomGenerator.nextInt(5);
        switch (codeIdx) {
            case 0:
                code = WeatherCode.SUNNY;
                break;
            case 1:
                code = WeatherCode.CLOUD;
                break;
            case 2:
                code = WeatherCode.CLOUDY;
                break;
            case 3:
                code = WeatherCode.RAIN;
                break;
            case 4:
                code = WeatherCode.SNOW;
                break;
            case 5:
                code = WeatherCode.THUNDER;
                break;
        }
        postDto.setWeatherCode(code);

        return postRepository.save(Post.builder()
//                .prediction(predictionService.findOne(postDto.getUserId())) // TODO: 수정 필요
                .user(userService.findOne(postDto.getUserId()))
                .weatherCode(postDto.getWeatherCode())
                .imageUrl(postDto.getImageUrl())
                .text(postDto.getText())
                .nx(postDto.getNx())
                .ny(postDto.getNy())
                .build());
    }

    @Transactional(readOnly = false)
    public PostLike createPostLike(PostLikeDto postLikeDto) {
        Post post = findOne(postLikeDto.getPostId());
        PostLike postLike = postLikeRepository.findByPostIdAndUserId(postLikeDto.getPostId(), postLikeDto.getUserId());
        if (!Objects.isNull(postLike)) {
            throw new PostException("Already post like!");
        }

        increasePostLikeCount(post);

        return postLikeRepository.save(PostLike.builder()
                .post(post)
                .user(userService.findOne(postLikeDto.getUserId()))
                .build());
    }

    @Transactional(readOnly = false)
    public void increasePostLikeCount(Post post) {
        post.setLikeCount(post.getLikeCount() + 1);
        postRepository.save(post);
    }

    public Post findOne(Long id) {
        return postRepository.findOne(id);
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
