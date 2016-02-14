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

import basketball.json.bean.PlayerJsonBean;
import basketball.json.bean.response.JsonResponse;
import basketball.json.bean.response.ListJsonResponse;
import basketball.service.PlayerService;


@Controller
@RequestMapping("/player")
public class PlayerController {

    private final Logger logger = LoggerFactory.getLogger(PlayerController.class);

    @Autowired
    private PlayerService playerService;
    
    @RequestMapping(method = RequestMethod.GET)
	String get() {
		return "player";
	}
    
    @RequestMapping(value= "/getPlayers", method=RequestMethod.POST)
    @ResponseBody
    public ListJsonResponse<PlayerJsonBean> getPlayers(@RequestParam int jtStartIndex, @RequestParam int jtPageSize) { 
    	List<PlayerJsonBean> records = playerService.find(new PageRequest(jtStartIndex, jtPageSize));
		long TotalRecordCount = playerService.count();
		ListJsonResponse<PlayerJsonBean> response = new ListJsonResponse<PlayerJsonBean>("OK", records, jtStartIndex, TotalRecordCount);
		return response;
    }
    
    @RequestMapping(value= "/createPlayer", method=RequestMethod.POST)
    @ResponseBody
    public ListJsonResponse<PlayerJsonBean> createPlayer(@ModelAttribute PlayerJsonBean playerJsonBean) { 
		this.playerService.save(playerJsonBean.player());
		return getPlayers((int) (playerService.count()/11), 10);
    }
    
    @RequestMapping(value= "/updatePlayer", method=RequestMethod.POST)
    @ResponseBody
    public ListJsonResponse<PlayerJsonBean> updatePlayer(@ModelAttribute PlayerJsonBean playerJsonBean) { 
		this.playerService.update(playerJsonBean.player());
		return getPlayers((int) (playerService.count()/11), 10);
    }
    
    @RequestMapping(value= "/deletePlayer", method=RequestMethod.POST)
    @ResponseBody
    public ListJsonResponse<PlayerJsonBean> deletePlayer(@RequestParam long id) { 
    	playerService.deleteById(id);
    	return getPlayers((int) (playerService.count()/11), 10);
    }
}
