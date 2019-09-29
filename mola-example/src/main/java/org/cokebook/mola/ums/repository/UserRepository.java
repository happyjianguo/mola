package org.cokebook.mola.ums.repository;

import org.cokebook.mola.ums.MockDB;
import org.cokebook.mola.ums.model.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 *
 * @date 2019/9/25 17:16
 */
@Repository
@Component
public class UserRepository {

    /**
     * 根据主键 id 查询用户
     *
     * @param id
     * @return
     */
    public User query(long id) {
        return new User(id, MockDB.ID_NAMES.get(id));
    }

    /**
     * @param user
     */
    public void updateUserName(User user) {
        if (MockDB.ID_NAMES.containsKey(user.getId())) {
            MockDB.ID_NAMES.put(user.getId(), user.getName());
        }
    }

    public void insert(User user) {
        MockDB.ID_NAMES.put(user.getId(), user.getName());
    }

}
