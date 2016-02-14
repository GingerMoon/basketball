package basketball.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import basketball.domain.Game;
import basketball.domain.GameStaticalData;
import basketball.domain.Player;
import basketball.json.bean.GameStaticalDataJsonBean;
import basketball.respository.GameStaticalDataRepository;
import basketball.respository.GamesRepository;
import basketball.respository.PlayerRepository;

@Service
public class GameStaticalDataService {

	@Autowired
	GamesRepository gameRespository;

	@Autowired
	PlayerRepository playerRespository;

	@Autowired
	GameStaticalDataRepository gameStaticalDataRespository;
	
	private List<GameStaticalDataJsonBean> beans2jsonBeans(List<GameStaticalData> beans) {
		List<GameStaticalDataJsonBean> jsnBeans = new ArrayList<GameStaticalDataJsonBean>();
		for(GameStaticalData bean : beans) {
			GameStaticalDataJsonBean jsnBean = new GameStaticalDataJsonBean(bean);
			jsnBeans.add(jsnBean);
		}
		return jsnBeans;
	}

	@Transactional(readOnly = false)
	public void save(GameStaticalData gameStaticalData) {
		Player player = this.playerRespository.findById(gameStaticalData.getPlayer().getId());
		this.playerRespository.save(player);
		gameStaticalData.setPlayer(player);
		Game game = this.gameRespository.findById(gameStaticalData.getGame().getId());
		this.gameRespository.save(game);
		gameStaticalData.setGame(game);
		gameStaticalDataRespository.save(gameStaticalData);
	}
	
	public List<GameStaticalDataJsonBean> findAll(Pageable pageable) {
		List<GameStaticalData> games = this.gameStaticalDataRespository.findAll(pageable).getContent();
		return beans2jsonBeans(games);
	}

	public GameStaticalData findById(long id) {
		return this.gameStaticalDataRespository.findById(id);
	}

	public void deleteById(long id) {
		this.gameStaticalDataRespository.delete(id);
	}
	
	public long count() {
		return this.gameStaticalDataRespository.count();		
	}

	public List<GameStaticalData> findStaticalDataByGameId(long gameId) {
		Game game = new Game();
		game.setId(gameId);
		return (List<GameStaticalData>) this.gameStaticalDataRespository.findByGame(game);
	}
	
	public List<GameStaticalDataJsonBean> findStaticalDataJSONByGameId(long gameId) {
		Game game = new Game();
		game.setId(gameId);
		List<GameStaticalData> beans = (List<GameStaticalData>) this.gameStaticalDataRespository.findByGame(game);
		return beans2jsonBeans(beans);
	}

//	public GameStaticalData findByGamePlayer(long gameId, long playerId) {
//		GameStaticalData gameStaticalData = null;
//		gameStaticalData = this.gameStaticalDataRespository.findByGamePlayer(gameId, playerId);
//		return gameStaticalData;
//	}
}
