package org.cokebook.mola.example;

import org.cokebook.mola.boot.EnableDDD;
import org.cokebook.mola.example.domain.User;
import org.cokebook.mola.example.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * @author wuming
 * @date 2019/7/7 16:20
 */

@EnableDDD
@SpringBootApplication
public class Bootstrap {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(Bootstrap.class, args);

        UserRepository userRepository = applicationContext.getBean(UserRepository.class);
        User user = userRepository.find(1);
        user.changeName("Test,Now");
        System.out.println("=========== user = " + user);

        System.exit(1);
    }

}
