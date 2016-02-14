package basketball.respository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import basketball.domain.Player;

@Repository
public interface PlayerRepository extends PagingAndSortingRepository<Player, Long> {

	Player findByUsername(String username);

	Player findById(Long id);

}
