package onetomany.Users;

import java.util.Date;
import java.util.List;


import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import onetomany.Items.Item;
import onetomany.Items.ItemsRepository;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import java.io.IOException;

/**
 *
 * @author Vivek Bengre
 *
 */

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ItemsRepository itemRepository;


    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @GetMapping(path = "/users")
    List<User> getAllUsersss(){
        for (User useer:userRepository.findAll()) {
            useer.setLastLoggin();
            userRepository.save(useer);
        }
        return userRepository.findAll();
    }
    @GetMapping(path = "/users/{id}")
    User getAllUser(@PathVariable int id){


        return  userRepository.findById(id);
    }

    @GetMapping(path = "/users/u/{username}")
    User getUser (@PathVariable String username){
      return userRepository.findByUsername(username);
    }


//    @GetMapping(path = "/loginEmail/{email}/{password}/")
//    User getUserById( @PathVariable String email, @PathVariable String password){
//        User temp= userRepository.findByEmailId(email);
//        if (temp.getUserPassword().equals(password))
//            return temp;
//        return null;
//    }
//    @GetMapping(path = "/us/{username}/{password}/")
//    User getUserByUsername( @PathVariable String username, @PathVariable String password){
//        User temp= userRepository.findByUsername(username);
//        if (temp.getUserPassword().equals(password))
//            return temp;
//        return null;
//    }


//    @GetMapping("/users/getReports/{id}/")
//    List<Reports> add(@PathVariable int id){
//        User tempUser= userRepository.findById(id);
//        if(tempUser == null)
//            return null;
//        return tempUser.getReports();
//    }


    
    @PostMapping(path = "/users")
    String createUser(@RequestBody User user){
        if (user == null)
            return failure;
        user.setJoiningDate(new Date());
        user.setLastLoggin();
        user.setIfActive(true);
        userRepository.save(user);

        return success;
    }
    @PostMapping(path = "/users/addItem/{username}")
    String createUser(@RequestBody Item item, @PathVariable String username){
        User tempUser= userRepository.findByUsername(username);
        if(tempUser == null)
            return failure;
        if(item == null)
            return failure;
        Item tempItem = itemRepository.findById(item.getId());
       
        tempItem.addLikedByUser(tempUser);
        itemRepository.save(tempItem);
        tempItem = itemRepository.findById(item.getId());
        tempUser.addItem(tempItem);
        userRepository.save(tempUser);
       
        return success;

    }

 

    @PutMapping("/users/{id}/{password}")
    User updateUser(@PathVariable int id, @PathVariable String password, @RequestBody User request){
        User user = userRepository.findById(id);

        if(user == null || !user.getUserPassword().equals(password))
            return null;
        userRepository.save(request);
        return userRepository.findById(id);
    }





    @DeleteMapping(path = "/users/{id}")
    String deleteUser(@PathVariable int id){
        User temp= userRepository.findById(id);
      
       
        userRepository.delete(temp);

        return success;
    }
    @PostMapping("/users/{username}/profile-image")
    public String uploadProfileImage(@PathVariable String username, @RequestParam("image") MultipartFile imageFile) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return "User not found";
        }

        try {
            user.setProfileImage(imageFile.getBytes());
            userRepository.save(user);
            return "Profile image uploaded successfully";
        } catch (IOException e) {
            return "Failed to upload profile image";
        }
    }

 

    @GetMapping("/users/{username}/profile-image")
    public ResponseEntity<byte[]> getProfileImage(@PathVariable String username) {
        User user = userRepository.findByUsername(username);
        if (user == null || user.getProfileImage() == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(user.getProfileImage());
    }

    @DeleteMapping("/users/{username}/profile-image")
    public String deleteProfileImage(@PathVariable String username) {
        User user = userRepository.findByUsername(username);
        if (user == null || user.getProfileImage() == null) {
            return "User not found or profile image not set";
        }

        user.setProfileImage(null);
        userRepository.save(user);
        return "Profile image deleted successfully";
    }
    //test


}

