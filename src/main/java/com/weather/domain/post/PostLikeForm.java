package com.weather.domain.post;

import lombok.Data;

/**
 * Created by godong9 on 2017. 7. 29..
 */

@Data
public class PostLikeForm {
    private Long userId;
    private Long postId;
}
