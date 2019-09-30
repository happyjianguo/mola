package org.cokebook.mola.ums;

import org.cokebook.mola.ModelFactory;
import org.cokebook.mola.injector.Injector;
import org.cokebook.mola.ums.model.User;
import org.cokebook.mola.ums.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * 该示例用于简单的演示在 Spring boot 框架下
 * ModelFactory & Repository 的使用
 *
 * @date 2019/9/25 17:47
 */
@SpringBootApplication
public class Demo {

    public static void main(String[] args) throws Exception {
        ApplicationContext applicationContext = SpringApplication.run(Demo.class, args);
        UserRepository userRepository = applicationContext.getBean(UserRepository.class);
        showUseRepository(userRepository);
        showUseModelFactory(applicationContext);
        showUseRepositoryWhitArrayResult(userRepository);
        showUseRepositoryWithCustomerType(userRepository);
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

    /**
     * 演示/测试仓库返回数组场景
     *
     * @param userRepository
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private static void showUseRepositoryWhitArrayResult(UserRepository userRepository) throws NoSuchFieldException, IllegalAccessException {
        User[] users = userRepository.query(Arrays.asList(1L, 2L));
        if (users.length > 0) {
            System.out.println("user array [0] = " + users[0]);
            users[0].changeName("xxxxx");
            Field field = User.class.getDeclaredField("userRepository");
            field.setAccessible(true);
            Assert.notNull(field.get(users[0]));
        }
    }

    @Bean
    public Injector.Processor tupleProcessor() {
        return new Injector.Processor() {
            @Override
            public void process(Object model, Injector injector) {
                if (model instanceof UserRepository.Tuple) {
                    UserRepository.Tuple tuple = (UserRepository.Tuple) model;
                    injector.inject(tuple.first);
                    injector.inject(tuple.second);
                }
            }
        };
    }

    /**
     * 演示如何应对用户自定义的返回值类型处理
     *
     * @param userRepository
     * @throws Exception
     */
    private static void showUseRepositoryWithCustomerType(UserRepository userRepository) throws Exception {
        UserRepository.Tuple<Long, User> tuple = userRepository.queryTuple(1L);
        Field field = User.class.getDeclaredField("userRepository");
        field.setAccessible(true);
        System.out.println("tuple.second = " + tuple.second);
        Assert.notNull(field.get(tuple.second));
    }

}
