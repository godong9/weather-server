package com.weather.domain.post;

import com.weather.domain.prediction.Prediction;
import com.weather.domain.user.User;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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
     * @apiSuccess {Number} prediction_id 기상청 예측 id
     * @apiSuccess {Number} user_id 유저 id
     * @apiSuccess {Number} code 날씨 코드
     * @apiSuccess {String} text 댓글
     * @apiSuccess {Number} nx X 좌표
     * @apiSuccess {Number} ny Y 좌표
     * @apiSuccess {Date} created_at 생성 시각
     */
    @PostMapping("/posts")
    public PostResult createPost(@RequestBody @Valid PostForm postForm) {
        Post post = postService.create(modelMapper.map(postForm, PostDto.class));
        PostResult postResult = modelMapper.map(post, PostResult.class);
        postResult.setUserId(post.getUser().getId());
        postResult.setCode(post.getWeatherCode().getCode());
        return postResult;
    }

    @GetMapping("/posts")
    public List<PostResult> getPosts(@RequestParam("nx") Integer nx, @RequestParam("ny") Integer ny) {
        List<Post> postList = postService.findByNxAndNy(nx, ny);
        return postList.stream()
                .map(post -> {
                    PostResult postResult = modelMapper.map(post, PostResult.class);

                    User user = post.getUser();
                    postResult.setUserId(user.getId());
                    postResult.setUserNickname(user.getNickname());
                    postResult.setCode(post.getWeatherCode().getCode());

                    // TODO: 주석 해제
//                    Prediction prediction = post.getPrediction();
//                    postResult.setPredictionId(prediction.getId());
//                    postResult.setPredictionTemperature(prediction.getTemperature());
//                    postResult.setPredictionHumidity(prediction.getHumidity());
//                    postResult.setPredictionRainProp(prediction.getRainProp());

                    return postResult;
                })
                .collect(Collectors.toList());
    }
}
