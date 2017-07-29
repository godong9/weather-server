package com.weather.domain.post;

import com.weather.domain.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by godong9 on 2017. 7. 29..
 */

@Transactional(readOnly = true)
@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

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

    public List<Post> findByNxAndNy(Integer nx, Integer ny) {
        return postRepository.findByNxAndNy(nx, ny);
    }
}
