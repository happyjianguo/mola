package org.cokebook.mola.example.domain;

import lombok.Data;
import org.cokebook.mola.Model;
import org.cokebook.mola.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * @author wuming
 * @date 2019/7/7 16:41
 */
@Model
@Data
public class User {

    @Autowired
    private UserRepository repository;

    private int id;
    private String name;
    private int age;
    private Map<Integer, Book> books;

    public void changeName(String name) {
        this.name = name;
    }
}
