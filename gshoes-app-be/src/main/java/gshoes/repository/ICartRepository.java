package gshoes.repository;

import gshoes.model.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface ICartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByCustomerName(String customerName);
}
