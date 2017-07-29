package com.weather.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by godong9 on 2017. 7. 29..
 */

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByNxAndNy(Integer nx, Integer ny);
}
