package basketball.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import basketball.domain.Game;
import basketball.json.bean.GameJsonBean;
import basketball.respository.GamesRepository;

@Service
public class GameService {

	@Autowired
	GamesRepository gamesRespository;
	
	private List<GameJsonBean> gamesEntities2json(List<Game> games) {
		List<GameJsonBean> games_jsn = new ArrayList<GameJsonBean>();
		for(Game game : games) {
			GameJsonBean itemjsn = new GameJsonBean(game);
			games_jsn.add(itemjsn);
		}
		return games_jsn;
	}

	@Transactional(readOnly = false)
	public void save(Game game) {
		gamesRespository.save(game);
	}
	
	public List<GameJsonBean> findAll(Pageable pageable) {
		List<Game> games = this.gamesRespository.findAll(pageable).getContent();
		return gamesEntities2json(games);
	}

	public Game findById(long id) {
		return this.gamesRespository.findById(id);
	}

	public void deleteById(long id) {
		this.gamesRespository.delete(id);
	}
	
	public long count() {
		return this.gamesRespository.count();		
	}
}
