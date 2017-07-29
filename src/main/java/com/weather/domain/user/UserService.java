package com.weather.domain.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.UUID;

/**
 * Created by godong9 on 2017. 7. 29..
 */

@Transactional(readOnly = true)
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User findOne(Long id) {
        return userRepository.findOne(id);
    }

    @Transactional(readOnly = false)
    public User create() {
        return userRepository.save(User.builder()
                .nickname(generateUserNickname())
                .uuid(UUID.randomUUID().toString())
                .build());
    }

    public String generateUserNickname() {
        String[] prefixArray = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };
        String[] nameArray = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };

        Random randomGenerator = new Random();

        int prefixIdx = randomGenerator.nextInt(10);
        int nameIdx = randomGenerator.nextInt(10);

        return prefixArray[prefixIdx] + nameArray[nameIdx];
    }
}
