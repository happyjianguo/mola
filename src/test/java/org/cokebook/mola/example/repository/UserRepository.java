package org.cokebook.mola.example.repository;

import org.cokebook.mola.Factory;
import org.cokebook.mola.example.domain.Book;
import org.cokebook.mola.example.domain.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wuming
 * @date 2019/7/7 16:41
 */
@Repository
public class UserRepository {

    public User $new(Factory<User> factory) {
        return factory.build();
    }

    public void updateUserName(User user) {
        System.out.println("========== update user name ========" + user);
    }

    public User find(int id) {
        User user = new User();
        user.setName("Test," + System.currentTimeMillis());
        user.setAge(20);
        user.setId(id);
        Map books = new HashMap();
        books.put(1, new Book());
        books.put(2, new Book());
        user.setBooks(books);
        return user;
    }

}
