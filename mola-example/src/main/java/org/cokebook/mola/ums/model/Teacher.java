package org.cokebook.mola.ums.model;

import org.cokebook.mola.ums.CustomerModel;
import org.cokebook.mola.ums.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 教师类
 * Note: 该类仅用于展示扩展对自定义标识注解的支持
 * Created by xm on 2019/10/1.
 */
@CustomerModel
public class Teacher {
    @Autowired
    private UserRepository userRepository;
    private long id;

    public Teacher(long id) {
        this.id = id;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
