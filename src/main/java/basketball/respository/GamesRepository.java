package basketball.respository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import basketball.domain.Game;


@Repository
public interface GamesRepository extends PagingAndSortingRepository<Game, Long> {

	Game findById(long id);
	
	Page<Game> findAll(Pageable pageable);

	@Query("select count(*) from Game g")
	long count();
}