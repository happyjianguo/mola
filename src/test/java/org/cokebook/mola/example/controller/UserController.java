package org.cokebook.mola.example.controller;

import com.google.common.collect.Maps;
import org.cokebook.mola.example.domain.User;
import org.cokebook.mola.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author wuming
 * @date 2019/7/7 16:35
 */
@RestController
@RequestMapping
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/book/new")
    public Object add(User user) {
        User dbUser = userRepository.$new(() -> user);
        Map<Object, Object> result = Maps.newHashMap();
        result.put("id", user.getId());
        return result;
    }

}
