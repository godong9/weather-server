package com.weather.domain.user;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by godong9 on 2017. 7. 29..
 */

@Slf4j
@RestController
public class UserController {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    /**
     * @api {post} /users/signup Signup User
     * @apiName UserSignup
     * @apiGroup User
     *
     * @apiSuccess {Number} id 유저 id
     * @apiSuccess {String} nickname 유저 닉네임
     */
    @PostMapping("/users/signup")
    public UserResult userSignup() {
        User user = userService.create();
        return modelMapper.map(user, UserResult.class);
    }
}
