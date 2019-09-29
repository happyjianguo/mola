package org.cokebook.mola.ums;

import org.cokebook.mola.ModelFactory;
import org.cokebook.mola.ums.model.User;
import org.cokebook.mola.ums.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * 该示例用于简单的演示在 Spring boot 框架下
 * ModelFactory & Repository 的使用
 *
 * @author fengzao
 * @date 2019/9/25 17:47
 */
@SpringBootApplication
public class Demo {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(Demo.class, args);
        UserRepository userRepository = applicationContext.getBean(UserRepository.class);
        showUseRepository(userRepository);
        showUseModelFactory(applicationContext);

    }

    /**
     * 演示如何使用 ModelFactory 快捷创建Model 对象
     *
     * @param applicationContext
     */
    private static void showUseModelFactory(ApplicationContext applicationContext) {
        ModelFactory modelFactory = applicationContext.getBean(ModelFactory.class);


        User newUser = modelFactory.<User>create(() -> new User(100L, "test"));
        newUser.save();

        System.out.println();
        System.out.println("----------------------------------- user id = " + newUser.getId() + " and name = " + MockDB.ID_NAMES.get(newUser.getId()));
        System.out.println();
    }

    /**
     * 演示如何使用 Repository 获取 Model 对象
     *
     * @param userRepository
     */
    private static void showUseRepository(UserRepository userRepository) {
        User user = userRepository.query(1);
        if (user != null) {
            user.changeName("hello");
        }
        System.out.println();
        System.out.println("----------------------------------- user id = 1 and name = " + MockDB.ID_NAMES.get(user.getId()));
        System.out.println();
    }
}
