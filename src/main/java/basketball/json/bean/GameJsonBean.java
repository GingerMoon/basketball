/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basketball.json.bean;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.NotEmpty;

import basketball.domain.Player;
import basketball.domain.Game;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GameJsonBean {

	private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	private String id = "-1";
	private String time = dateFormat.format(new Date());
	private List<GameStaticalDataJsonBean> gameStaticalDataJsonBeanList = new ArrayList<GameStaticalDataJsonBean>();
	
	public GameJsonBean() {
		
	}
	
	public GameJsonBean(Game game) {
		this.id = new Long(game.getId()).toString();
		this.time = dateFormat.format(game.getDate());
	}
	
	public Game game() {
		Game game = new Game();
		game.setId(Long.parseLong(id));
		try {
			game.setDate(dateFormat.parse(this.time));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return game;
	}

	@JsonProperty("id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	@JsonProperty("time")
	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@JsonProperty("gameStaticalDataList")
	public List<GameStaticalDataJsonBean> getGameStaticalDataJsonBean() {
		return gameStaticalDataJsonBeanList;
	}

	public void setGameStaticalDataJsonBean(List<GameStaticalDataJsonBean> gameStaticalDataJsonBeanList) {
		this.gameStaticalDataJsonBeanList = gameStaticalDataJsonBeanList;
	}
	
	
}
