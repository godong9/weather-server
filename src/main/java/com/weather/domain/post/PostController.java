package com.weather.domain.post;

import com.weather.domain.user.User;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
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
     * @api {post} /posts Create Post
     * @apiName PostCreate
     * @apiGroup Post
     *
     * @apiDescription 포스트 생성
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

    /**
     * @api {get} /posts Get Post list
     * @apiName PostList
     * @apiGroup Post
     *
     * @apiDescription nx, ny에 존재하는 포스트를 배열 형태로 내려줌
     *
     * @apiParam {Number} nx X 좌표
     * @apiParam {Number} ny Y 좌표
     *
     * @apiExample {curl} Example:
     *      http://localhost:9000/posts?nx=120&ny=30
     *
     * @apiSuccess {Number} id 포스트 id
     * @apiSuccess {Number} prediction_id 기상청 예측 id
     * @apiSuccess {String} prediction_temperature 기상청 예측 기온
     * @apiSuccess {String} prediction_humidity 기상청 예측 습도
     * @apiSuccess {String} prediction_rain_prop 기상청 예측 강수확률
     * @apiSuccess {Number} user_id 유저 id
     * @apiSuccess {String} user_nicknam 유저 닉네임
     * @apiSuccess {Number} code 날씨 코드
     * @apiSuccess {String} text 댓글
     * @apiSuccess {Number} nx X 좌표
     * @apiSuccess {Number} ny Y 좌표
     * @apiSuccess {Boolean} is_liked 좋아요 여부
     * @apiSuccess {Date} base_date 기상청 발표 시각
     * @apiSuccess {Date} prediction_date 기상청 예보 시각
     * @apiSuccess {Date} created_at 생성 시각
     */
    @GetMapping("/posts")
    public List<PostResult> getPosts(@RequestParam("nx") Integer nx, @RequestParam("ny") Integer ny) {
        List<Post> postList = postService.findByNxAndNy(nx, ny);
        return postList.stream()
                .map(this::getPostResult)
                .collect(Collectors.toList());
    }

    /**
     * @api {get} /posts/:id Get Post Detail
     * @apiName PostDetail
     * @apiGroup Post
     *
     * @apiDescription 포스트 상세 정보
     *
     * @apiParam {Number} user_id 유저 id
     *
     * @apiExample {curl} Example:
     *      http://localhost:9000/posts/1?user_id=1
     *
     * @apiSuccess {Number} id 포스트 id
     * @apiSuccess {Number} prediction_id 기상청 예측 id
     * @apiSuccess {String} prediction_temperature 기상청 예측 기온
     * @apiSuccess {String} prediction_humidity 기상청 예측 습도
     * @apiSuccess {String} prediction_rain_prop 기상청 예측 강수확률
     * @apiSuccess {Number} user_id 유저 id
     * @apiSuccess {String} user_nicknam 유저 닉네임
     * @apiSuccess {Number} code 날씨 코드
     * @apiSuccess {String} text 댓글
     * @apiSuccess {Number} nx X 좌표
     * @apiSuccess {Number} ny Y 좌표
     * @apiSuccess {Boolean} is_liked 좋아요 여부
     * @apiSuccess {Date} base_date 기상청 발표 시각
     * @apiSuccess {Date} prediction_date 기상청 예보 시각
     * @apiSuccess {Date} created_at 생성 시각
     */
    @GetMapping("/posts/{id}")
    public PostResult getPost(@PathVariable @Valid Long id, @RequestParam("user_id") Long userId) {
        Post post = postService.findOne(id, userId);
        return getPostResult(post);
    }

    /**
     * @api {post} /posts Create Post like
     * @apiName PostLike
     * @apiGroup Post
     *
     * @apiDescription 포스트 좋아요
     *
     * @apiParam {Number} post_id 포스트 id
     * @apiParam {Number} user_id 유저 id
     *
     * @apiSuccess {Number} id 포스트 id
     * @apiSuccess {Number} prediction_id 기상청 예측 id
     * @apiSuccess {String} prediction_temperature 기상청 예측 기온
     * @apiSuccess {String} prediction_humidity 기상청 예측 습도
     * @apiSuccess {String} prediction_rain_prop 기상청 예측 강수확률
     * @apiSuccess {Number} user_id 유저 id
     * @apiSuccess {String} user_nicknam 유저 닉네임
     * @apiSuccess {Number} code 날씨 코드
     * @apiSuccess {String} text 댓글
     * @apiSuccess {Number} nx X 좌표
     * @apiSuccess {Number} ny Y 좌표
     * @apiSuccess {Boolean} is_liked 좋아요 여부
     * @apiSuccess {Date} base_date 기상청 발표 시각
     * @apiSuccess {Date} prediction_date 기상청 예보 시각
     * @apiSuccess {Date} created_at 생성 시각
     */
    @PostMapping("/posts/like")
    public void createPostLike(@RequestBody @Valid PostLikeForm postLikeForm) {
        postService.createPostLike(modelMapper.map(postLikeForm, PostLikeDto.class));
    }

    private PostResult getPostResult(Post post) {
        PostResult postResult = modelMapper.map(post, PostResult.class);
        postResult.setCode(post.getWeatherCode().getCode());
        postResult.setIsLiked(post.getIsLiked());

        User user = post.getUser();
        postResult.setUserId(user.getId());
        postResult.setUserNickname(user.getNickname());

        // TODO: 주석 해제
//                    Prediction prediction = post.getPrediction();
//                    postResult.setPredictionId(prediction.getId());
//                    postResult.setPredictionTemperature(prediction.getTemperature());
//                    postResult.setPredictionHumidity(prediction.getHumidity());
//                    postResult.setPredictionRainProp(prediction.getRainProp());
//                    postResult.setBaseDate(prediction.getBaseDate());
//                    postResult.setPredictionDate(prediction.getPredictionDate());

        return postResult;
    }

}
