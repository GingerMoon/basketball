package basketball.controller;

import java.text.ParseException;
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

import basketball.json.bean.GameJsonBean;
import basketball.json.bean.response.JsonResponse;
import basketball.json.bean.response.ListJsonResponse;
import basketball.service.GameService;


@Controller
@RequestMapping("/games")
public class GamesController {

    private final Logger logger = LoggerFactory.getLogger(GamesController.class);

    @Autowired
    private GameService gamesService;
    
    @RequestMapping(method = RequestMethod.GET)
	String get() {
		return "games";
	}
    
    @RequestMapping(value= "/getGames", method=RequestMethod.POST)
    @ResponseBody
    public ListJsonResponse<GameJsonBean> getGames(@RequestParam int jtStartIndex, @RequestParam int jtPageSize) { 
    	List<GameJsonBean> records = gamesService.findAll(new PageRequest(jtStartIndex, jtPageSize));
		long TotalRecordCount = gamesService.count();
		ListJsonResponse<GameJsonBean> response = new ListJsonResponse<GameJsonBean>("OK", records, jtStartIndex, TotalRecordCount);
		return response;
    }
    
    @RequestMapping(value= "/createGame", method=RequestMethod.POST)
    @ResponseBody
    public ListJsonResponse<GameJsonBean> createGame(@ModelAttribute GameJsonBean gameJsonBean) { 
    	this.gamesService.save(gameJsonBean.game());
    	return getGames((int) (gamesService.count()/11), 10);
    }
    
    @RequestMapping(value= "/removeGame", method=RequestMethod.POST)
    @ResponseBody
    public ListJsonResponse<GameJsonBean> removeGame(@RequestParam long id) { 
    	gamesService.deleteById(id);
    	return getGames((int) (gamesService.count()/11), 10);
    }
}
