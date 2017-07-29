package com.weather.domain.post;

import lombok.Data;

import java.util.List;

/**
 * Created by godong9 on 2017. 7. 30..
 */

@Data
public class TotalPostStatResult {
    private List<PostStatResult> postStatResultList;
}
