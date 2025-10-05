package onetomany.Sellers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {
    Seller findById(long id);
    Seller findByUsername(String username);
    boolean existsByUsername(String username);
}
