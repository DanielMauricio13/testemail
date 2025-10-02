package onetomany.Sellers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import jakarta.validation.Validator;
import jakarta.validation.ConstraintViolationException;

import java.util.*;

@Validated
@RestController
@RequestMapping("/sellers")
public class SellerController {

    @Autowired
    private SellerRepository sellerRepository;

    // GET all sellers
    @GetMapping
    public List<Seller> getAll() {
        return sellerRepository.findAll();
    }

    // GET seller by ID
    @GetMapping("/{id}")
    public Seller getById(@PathVariable long id) {
        Seller seller = sellerRepository.findById(id);
        if (seller == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Seller not found");
        }
        return seller;
    }

    // GET seller by username
    @GetMapping("/u/{username}")
    public Seller getByUsername(@PathVariable String username) {
        Seller seller = sellerRepository.findByUsername(username);
        if (seller == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Seller not found");
        }
        return seller;
    }

    // Check if username exists
    @GetMapping("/exists")
    public Map<String, Boolean> usernameExists(@RequestParam("username") String username) {
        return Map.of("exists", sellerRepository.existsByUsername(username));
    }

    // CREATE new seller
    @PostMapping
    public ResponseEntity<Seller> create(@Valid @RequestBody Seller seller) {
        if (sellerRepository.existsByUsername(seller.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }
        
        // Set defaults for null fields
        if (seller.getCreatedAt() == null) {
            seller.setCreatedAt(new Date());
        }
        if (seller.getActive() == null) {
            seller.setActive(true);
        }
        
        Seller saved = sellerRepository.save(seller);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // UPDATE seller
    @PutMapping("/{id}")
    public ResponseEntity<Seller> update(@PathVariable long id, @Valid @RequestBody Seller incoming) {
        Seller current = sellerRepository.findById(id);
        if (current == null) {
            return ResponseEntity.notFound().build();
        }
        
        // Check for username conflict
        if (!incoming.getUsername().equals(current.getUsername()) 
            && sellerRepository.existsByUsername(incoming.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        // Update fields
        current.setUsername(incoming.getUsername());
        current.setBio(incoming.getBio());
        current.setActive(incoming.getActive() != null ? incoming.getActive() : current.getActive());
        current.setRating(incoming.getRating() != null ? incoming.getRating() : current.getRating());
        current.setRatingsCount(incoming.getRatingsCount() != null ? incoming.getRatingsCount() : current.getRatingsCount());
        current.setTotalSales(incoming.getTotalSales() != null ? incoming.getTotalSales() : current.getTotalSales());
        
        if (incoming.getCreatedAt() != null) {
            current.setCreatedAt(incoming.getCreatedAt());
        }
        
        return ResponseEntity.ok(sellerRepository.save(current));
    }

    // DELETE seller
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!sellerRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        sellerRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Rate seller
    @PostMapping("/{id}/rate")
    public ResponseEntity<Seller> addRating(
            @PathVariable long id,
            @DecimalMin("0.0") @DecimalMax("5.0") @RequestParam double value) {
        Seller seller = sellerRepository.findById(id);
        if (seller == null) {
            return ResponseEntity.notFound().build();
        }
        
        // if null initialize
        if (seller.getRatingsCount() == null) {
            seller.setRatingsCount(0);
        }
        if (seller.getRating() == null) {
            seller.setRating(0.0);
        }
        
        // Calculate average rating
        double totalRating = seller.getRating() * seller.getRatingsCount() + value;
        seller.setRatingsCount(seller.getRatingsCount() + 1);
        seller.setRating(totalRating / seller.getRatingsCount());

        return ResponseEntity.ok(sellerRepository.save(seller));
    }

    // Increment sale count
    @PostMapping("/{id}/sale")
    public ResponseEntity<Seller> incrementSale(@PathVariable long id) {
        Seller seller = sellerRepository.findById(id);
        if (seller == null) {
            return ResponseEntity.notFound().build();
        }
        
        if (seller.getTotalSales() == null) {
            seller.setTotalSales(0);
        }
        seller.setTotalSales(seller.getTotalSales() + 1);
        
        return ResponseEntity.ok(sellerRepository.save(seller));
    }
}
