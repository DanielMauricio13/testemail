package onetomany.Items;

import java.util.Date;
import java.util.List;


import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
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
 * @author Daniel Pinilla
 *
 */

@RestController
public class ItemsController {

    @Autowired
    ItemsRepository itemsRepository;



    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @GetMapping(path = "/items")
    List<Item> getAllItems(){
       
        return itemsRepository.findAll();
    }
    @GetMapping(path = "/items/{id}")
    Item getAllUser(@PathVariable int id){


        return  itemsRepository.findById(id);
    }

    @GetMapping(path = "/items/u/{username}")
    Item getUser (@PathVariable String username){
      return itemsRepository.findByUsername(username);
    }

    @PostMapping(path = "/items/")
    String createItem(@RequestBody Item item){
        if (item == null)
            return failure;
        item.setCreationDate(new Date());
        item.setIfAvailable(true);
        itemsRepository.save(item);

        return success;
    }

 

    @PutMapping("/items/{id}")
    Item updateItem(@PathVariable int id, @RequestBody Item request){
        Item item = itemsRepository.findById(id);

        if(item == null)
            return null;
        itemsRepository.save(request);
        return itemsRepository.findById(id);
    }





    @DeleteMapping(path = "/items/{id}")
    String deleteItem(@PathVariable int id){
        Item temp= itemsRepository.findById(id);
        if (temp == null)
            return failure;

        itemsRepository.delete(temp);

        return success;
    }

    @PostMapping("/items/{id}/profile-image")
    public String uploadProfileImage(@PathVariable int id, @RequestParam("image") MultipartFile imageFile) {
        Item item = itemsRepository.findById(id);
        if (item == null) {
            return "Item not found";
        }

        try {
            item.setProfileImage(imageFile.getBytes());
            itemsRepository.save(item);
            return "Profile image uploaded successfully";
        } catch (IOException e) {
            return "Failed to upload profile image";
        }
    }

 

    @GetMapping("/items/{id}/profile-image")
    public ResponseEntity<byte[]> getProfileImage(@PathVariable int id) {
        Item item = itemsRepository.findById(id);
        if (item == null || item.getProfileImage() == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(item.getProfileImage());
    }

    @DeleteMapping("/items/{id}/profile-image")
    public String deleteProfileImage(@PathVariable int id) {
        Item item = itemsRepository.findById(id);
        if (item == null || item.getProfileImage() == null) {
            return "Item not found or profile image not set";
        }

        item.setProfileImage(null);
        itemsRepository.save(item);
        return "Profile image deleted successfully";
    }
    //test


    

   
    


}

