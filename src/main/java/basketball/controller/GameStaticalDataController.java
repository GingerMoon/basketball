package basketball.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import basketball.domain.Game;
import basketball.domain.GameStaticalData;
import basketball.domain.Player;
import basketball.json.bean.GameJsonBean;
import basketball.json.bean.GameStaticalDataJsonBean;
import basketball.json.bean.PlayerJsonBean;
import basketball.json.bean.response.JsonResponse;
import basketball.json.bean.response.ListJsonResponse;
import basketball.service.GameService;
import basketball.service.GameStaticalDataService;


@Controller
@RequestMapping("/gameStaticalData")
public class GameStaticalDataController {

    private final Logger logger = LoggerFactory.getLogger(GameStaticalDataController.class);

    @Autowired
    private GameStaticalDataService gameStaticalDataService;
    
    @Autowired
    private GameService gameService;
    
    @RequestMapping(method = RequestMethod.GET)
	String get(@ModelAttribute("gameId") Long gameId) {
		return "gameStaticalData";
	}
    
    @RequestMapping(value= "/getGameStaticalData", method=RequestMethod.GET)
    @ResponseBody
    public ListJsonResponse<GameStaticalDataJsonBean> getGameStaticalData(@RequestParam Long gameId) { 
    	List<GameStaticalDataJsonBean> records = gameStaticalDataService.findStaticalDataJSONByGameId(gameId);
		long TotalRecordCount = gameStaticalDataService.count();
		ListJsonResponse<GameStaticalDataJsonBean> response = new ListJsonResponse<GameStaticalDataJsonBean>("OK", records, 0, TotalRecordCount);
		return response;
    }
    
    @RequestMapping(value= "/addGameStaticalData", method=RequestMethod.POST)
    @ResponseBody
    public ListJsonResponse<GameStaticalDataJsonBean> addGameStaticalData(@RequestParam String gameId, @RequestParam String playerIds) { 
    	Game game = this.gameService.findById(Long.parseLong(gameId));
    	String [] players = playerIds.split(":");
    	for(String playerId : players) {
    		GameStaticalDataJsonBean gameStaticalDataJsonBean = new GameStaticalDataJsonBean();
    		gameStaticalDataJsonBean.setPlayerId(playerId);
    		gameStaticalDataJsonBean.setGameId(gameId);
    		this.gameStaticalDataService.save(gameStaticalDataJsonBean.gameStaticalData());
//    		GameStaticalData gameStaticalData  = this.gameStaticalDataService.findByGamePlayer(Long.parseLong(gameId), Long.parseLong(playerId));
//    		game.getGameStaticalDatas().add(gameStaticalData);
//    		this.gameService.save(game);
			
    	}
    	
		return getGameStaticalData(Long.parseLong(gameId));
    }
    
    @RequestMapping(value= "/updateMiss", method=RequestMethod.POST)
    @ResponseBody
    public ListJsonResponse<GameStaticalDataJsonBean> updateMiss(@RequestParam String gameId, @RequestParam Long rowId, @RequestParam String flag) {
    	GameStaticalData gameStaticalData = this.gameStaticalDataService.findById(rowId);
    	if(flag.equals("minus"))
    		gameStaticalData.setMiss(gameStaticalData.getMiss()-1);
    	else if(flag.equals("plus"))
    		gameStaticalData.setMiss(gameStaticalData.getMiss()+1);
    	this.gameStaticalDataService.save(gameStaticalData);
    	return getGameStaticalData(Long.parseLong(gameId));
    }
    
    @RequestMapping(value= "/updateScore", method=RequestMethod.POST)
    @ResponseBody
    public ListJsonResponse<GameStaticalDataJsonBean> updateScore(@RequestParam String gameId, @RequestParam Long rowId, @RequestParam String flag) {
    	GameStaticalData gameStaticalData = this.gameStaticalDataService.findById(rowId);
    	if(flag.equals("minus"))
    		gameStaticalData.setScore(gameStaticalData.getScore()-1);
    	else if(flag.equals("plus"))
    		gameStaticalData.setScore(gameStaticalData.getScore()+1);
    	this.gameStaticalDataService.save(gameStaticalData);
    	return getGameStaticalData(Long.parseLong(gameId));
    }
    
    @RequestMapping(value= "/getPlayers", method=RequestMethod.POST)
    @ResponseBody
    public ListJsonResponse<PlayerJsonBean> getPlayers(@RequestParam String gameId) {
    	List<GameStaticalData> list = this.gameStaticalDataService.findStaticalDataByGameId(Long.parseLong(gameId));
    	List<PlayerJsonBean> records = new ArrayList<PlayerJsonBean>();
    	for(GameStaticalData item : list) {
    		records.add(new PlayerJsonBean(item.getPlayer()));
    	}
		ListJsonResponse<PlayerJsonBean> response = new ListJsonResponse<PlayerJsonBean>("OK", records, 0, records.size());
		return response;
    }
    
    @RequestMapping(value= "/changeVictory", method=RequestMethod.POST)
    @ResponseBody
    public ListJsonResponse<GameStaticalDataJsonBean> changeVictory(@RequestParam String gameId, @RequestParam String playerIds, @RequestParam int action) {
    	String [] players = playerIds.split(":");
    	List<GameStaticalData> list = this.gameStaticalDataService.findStaticalDataByGameId(Long.parseLong(gameId));
    	for(String playerId : players) {
    		for(GameStaticalData item : list) {
    			if(Long.parseLong(playerId) == item.getPlayer().getId()) {
    				item.setVictory(action == 1 ? true : false);
    				this.gameStaticalDataService.save(item);
    			}
    			
    		}
    	}
		return getGameStaticalData(Long.parseLong(gameId));
    }
    
    @RequestMapping(value= "/removeGameStaticalData", method=RequestMethod.POST)
    @ResponseBody
    public ListJsonResponse<GameStaticalDataJsonBean> removeGameStaticalData(@RequestParam String gameId, @RequestParam long id) { 
    	gameStaticalDataService.deleteById(id);
    	return getGameStaticalData(Long.parseLong(gameId));
    }
}
