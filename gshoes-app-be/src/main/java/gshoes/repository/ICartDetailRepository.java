package gshoes.repository;

import gshoes.model.entity.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface ICartDetailRepository extends JpaRepository<CartDetail, Long> {

    void deleteByCart_IdAndName(long id, String name);
}
