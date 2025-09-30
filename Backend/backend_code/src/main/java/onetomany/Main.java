
package onetomany;

import java.util.Date;



import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import onetomany.Users.User;
import onetomany.Users.UserRepository;



@SpringBootApplication
//@EnableSwagger2g
class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    CommandLineRunner initUser(UserRepository userRepository){
        return args -> {
//            User user1= new User("Daniel", "dmvp01@iastate.edu", new Date(), "Daniel123", "DanielNew1", 21);
//           
//
//            userRepository.save(user1);


        };
    }

    // Create 3 users with their machines and phones
//    @Bean
//    CommandLineRunner initUser(UserRepository userRepository, MatchesRepository matchesRepository, ReportsRepository reportsRepository,HobbiesRepository hobbiesRepository) {
//        return args -> {
////
//            User user1 = new User("Daniel", "dmvp01@somemail.com", new Date(), "Daniel123", "danielUser");
//           
////            User updatedUser1 = userRepository.findById(user1.getId());
////         
////
////
////
////            userRepository.save(user1);
////           
//
//
//
//            user1 = userRepository.save(user1);
//          
//
//
////     
//    }
    @Bean
    public CommandLineRunner demo(UserRepository userRepository) {
        return (args) -> {
            // Create a new user and save it in the database
//            User newUser = new User("Test User", "testuser@example.com", new Date(), "TestUserPassword", "testUsername");
//            userRepository.save(newUser);
//
//            // List all users
//            System.out.println("Users found with findAll():");
//            System.out.println("-------------------------------");
//            for (User user : userRepository.findAll()) {
//                System.out.println(user.getName());
//            }
        };
    }

}

