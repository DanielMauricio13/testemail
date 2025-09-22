package onetomany.sellers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/sellers")
public class SellerController {

    private final SellerRepository sellers;

    public SellerController(SellerRepository sellers) {
        this.sellers = sellers;
    }

    // read
    @GetMapping
    public List<Seller> getAll() {
        return sellers.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Seller> getById(@PathVariable Long id) {
        return sellers.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // lookup by username
    @GetMapping("/u/{username}")
    public ResponseEntity<Seller> getByUsername(@PathVariable String username) {
        return sellers.findByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // create
    @PostMapping
    public ResponseEntity<Seller> create(@RequestBody Seller payload) {
        if (payload.getUsername() == null || payload.getUsername().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        if (sellers.existsByUsername(payload.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }

        // initialize defaults
        if (payload.getCreatedAt() == null) payload.setCreatedAt(new Date());
        if (payload.getActive() == null) payload.setActive(true);
        if (payload.getRating() == null) payload.setRating(0.0);
        if (payload.getRatingsCount() == null) payload.setRatingsCount(0);
        if (payload.getTotalSales() == null) payload.setTotalSales(0);

        Seller saved = sellers.save(payload);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // update
    @PutMapping("/{id}")
    public ResponseEntity<Seller> replace(
            @PathVariable Long id,
            @RequestBody Seller incoming
    ) {
        Optional<Seller> existingOpt = sellers.findById(id);
        if (existingOpt.isEmpty()) return ResponseEntity.notFound().build();

        Seller current = existingOpt.get();

        if (incoming.getUsername() == null || incoming.getUsername().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        if (!incoming.getUsername().equals(current.getUsername())
                && sellers.existsByUsername(incoming.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        current.setUsername(incoming.getUsername());
        current.setBio(incoming.getBio());
        current.setActive(incoming.getActive() != null ? incoming.getActive() : current.getActive());
        current.setRating(incoming.getRating() != null ? incoming.getRating() : current.getRating());
        current.setRatingsCount(incoming.getRatingsCount() != null ? incoming.getRatingsCount() : current.getRatingsCount());
        current.setTotalSales(incoming.getTotalSales() != null ? incoming.getTotalSales() : current.getTotalSales());
        if (incoming.getCreatedAt() != null) current.setCreatedAt(incoming.getCreatedAt());

        return ResponseEntity.ok(sellers.save(current));
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!sellers.existsById(id)) return ResponseEntity.notFound().build();
        sellers.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Rate seller
    @PostMapping("/{id}/rate")
    public ResponseEntity<Seller> addRating(@PathVariable Long id, @RequestParam double value) {
        Optional<Seller> opt = sellers.findById(id);
        if (opt.isEmpty()) return ResponseEntity.notFound().build();
        if (value < 0.0 || value > 5.0) return ResponseEntity.badRequest().build();

        Seller s = opt.get();
        double total = s.getRating() * s.getRatingsCount() + value;
        s.setRatingsCount(s.getRatingsCount() + 1);
        s.setRating(total / s.getRatingsCount());

        return ResponseEntity.ok(sellers.save(s));
    }

    @PostMapping("/{id}/sale")
    public ResponseEntity<Seller> incrementSale(@PathVariable Long id) {
        return sellers.findById(id)
                .map(s -> {
                    s.setTotalSales(s.getTotalSales() + 1);
                    return ResponseEntity.ok(sellers.save(s));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
