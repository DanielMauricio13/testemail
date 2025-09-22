package onetomany.Items;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Daniel Pinilla
 *
 */

public interface ItemsRepository extends JpaRepository<Item, Long> {
    Item findById(int id);

    Item findByUsername(String username);

}
