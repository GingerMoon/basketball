package basketball.respository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import basketball.domain.Game;
import basketball.domain.GameStaticalData;


@Repository
public interface GameStaticalDataRepository extends PagingAndSortingRepository<GameStaticalData, Long> {

	GameStaticalData findById(long id);
	
	List<GameStaticalData> findByGame(Game game);

	@Query("select count(*) from GameStaticalData s")
	long count();

//	@Query("select g from GameStaticalData g where g.game.id = :gameId and g.player.id = :playerId")
//	GameStaticalData findByGamePlayer(@Param("gameId") long gameId, @Param("playerId") long playerId);
}