package org.cokebook.mola.ums.model;

import org.cokebook.mola.Model;
import org.cokebook.mola.ums.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author fengzao
 * @date 2019/9/25 17:14
 */
@Model
public class User {

    @Autowired
    private UserRepository userRepository;

    public User() {
    }

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void changeName(String name) {
        this.name = name;
        userRepository.updateUserName(this);
    }

    public void save() {
        userRepository.insert(this);
    }

}
