package onetomany.Items;

import onetomany.Sellers.Seller;
import onetomany.Sellers.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Daniel Pinilla
 *
 */

@RestController
@RequestMapping("/items")
public class ItemsController {

    @Autowired
    ItemsRepository itemsRepository;
    @Autowired
    private SellerRepository sellerRepository;

    private String success = "{\"message\":\"success\"}";
    private String failure = "{\"message\":\"failure\"}";

    @GetMapping  // Remove "/items" - already in @RequestMapping
    List<Item> getAllItems(){
       
        return itemsRepository.findAll();
    }
    @GetMapping("/{id}")  // Remove "/items" prefix
    Item getAllUser(@PathVariable int id){
        return  itemsRepository.findById(id);
    }

    @GetMapping("/u/{username}")  // Remove "/items" prefix
    Item getUser (@PathVariable String username){
      return itemsRepository.findByUsername(username);
    }

    @PostMapping  // Remove "/items" prefix
    String createItem(@RequestBody Item item){
        if (item == null)
            return failure;
        item.setCreationDate(new Date());
        item.setIfAvailable(true);
        itemsRepository.save(item);

        return success;
    }

    // POST create item for a seller
    @PostMapping(path = "/seller/{sellerId}")
    ResponseEntity<Item> createItemWithSeller(@PathVariable long sellerId, @RequestBody Item item) {
        Seller seller = sellerRepository.findById(sellerId);
        if (seller == null) {
            return ResponseEntity.notFound().build();
        }

        if (item == null) {
            return ResponseEntity.badRequest().build();
        }

        item.setCreationDate(new Date());
        item.setIfAvailable(true);
        seller.addItem(item);
        Item savedItem = itemsRepository.save(item);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedItem);
    }

    @PutMapping("/{id}")
    Item updateItem(@PathVariable int id, @RequestBody Item request) {
        Item item = itemsRepository.findById(id);

        if (item == null)
            return null;
        itemsRepository.save(request);
        return itemsRepository.findById(id);
    }


    @DeleteMapping(path = "/{id}")
    String deleteItem(@PathVariable int id) {
        Item temp = itemsRepository.findById(id);
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
    // GET seller info for an item
    @GetMapping("/{id}/seller")
    public ResponseEntity<Map<String, Object>> getItemSeller(@PathVariable int id) {
        Item item = itemsRepository.findById(id);
        if (item == null || item.getSeller() == null) {
            return ResponseEntity.notFound().build();
        }

        Seller seller = item.getSeller();
        Map<String, Object> sellerInfo = Map.of(
                "id", seller.getId(),
                "username", seller.getUsername(),
                "rating", seller.getRating() != null ? seller.getRating() : 0.0,
                "totalSales", seller.getTotalSales() != null ? seller.getTotalSales() : 0
        );

        return ResponseEntity.ok(sellerInfo);
    }


}
