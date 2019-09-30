package org.cokebook.mola.ums.repository;

import org.cokebook.mola.ums.MockDB;
import org.cokebook.mola.ums.model.User;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/**
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

    public Tuple<Long, User> queryTuple(long id) {
        return new Tuple<>(id, query(id));
    }

    public User[] query(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return new User[0];
        }
        return ids.stream().map(id -> new User(id, MockDB.ID_NAMES.get(id)))
                .collect(Collectors.toList())
                .toArray(new User[ids.size()]);
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


    public static class Tuple<T1, T2> {
        public final T1 first;
        public final T2 second;

        public Tuple(T1 first, T2 second) {
            this.first = first;
            this.second = second;
        }
    }

}
