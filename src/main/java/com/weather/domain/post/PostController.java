package com.weather.domain.post;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by godong9 on 2017. 7. 29..
 */

@Slf4j
@RestController
public class PostController {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PostService postService;

    /**
     * @api {post} /posts Request Post create
     * @apiName PostCreate
     * @apiGroup Post
     *
     * @apiParam {Number} prediction_id 기상청 예측 id
     * @apiParam {Number} user_id 유저 id
     * @apiParam {Number} weather_code 날씨 코드
     * @apiParam {String} text 댓글
     * @apiParam {Number} nx X 좌표
     * @apiParam {Number} ny Y 좌표
     *
     * @apiSuccess {Number} id 포스트 id
     */
    @PostMapping("/posts")
    public PostResult createPost(@RequestBody @Valid PostForm postForm) {
        Post post = postService.create(modelMapper.map(postForm, PostDto.class));
        PostResult postResult = modelMapper.map(post, PostResult.class);
        postResult.setUserId(post.getUser().getId());
        postResult.setCode(post.getWeatherCode().getCode());
        return postResult;
    }
}
