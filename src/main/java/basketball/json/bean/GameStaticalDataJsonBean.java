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

import basketball.domain.GameStaticalData;
import basketball.domain.Player;
import basketball.domain.Game;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GameStaticalDataJsonBean {
	private String id = "-1";
	private String playerId = "-1";
	private String playerName = "";
	private String gameId = "-1";
	private String miss = "0";
	private String score = "0";
	private String victory = "X";
	    
	public GameStaticalDataJsonBean() {
		
	}
	
	public GameStaticalDataJsonBean(GameStaticalData gameStaticalData) {
		this.id = new Long(gameStaticalData.getId()).toString();
		this.playerId = new Long(gameStaticalData.getPlayer().getId()).toString();
		this.playerName = gameStaticalData.getPlayer().getUsername();
		this.gameId = new Long(gameStaticalData.getGame().getId()).toString();
		this.miss = new Integer(gameStaticalData.getMiss()).toString();
		this.score = new Integer(gameStaticalData.getScore()).toString();
		this.victory = gameStaticalData.getVictory() == true ? "V" : "X";
	}
	
	public GameStaticalData gameStaticalData() {
		GameStaticalData gameStaticalData = new GameStaticalData();
		gameStaticalData.setId(Long.parseLong(id));
		gameStaticalData.setMiss(Integer.parseInt(miss));
		gameStaticalData.setScore(Integer.parseInt(score));
		gameStaticalData.setVictory(victory.compareTo("V") == 0 ? true : false);
		Player player = new Player();
		player.setId(Long.parseLong(playerId));
		player.setUsername(playerName);
		gameStaticalData.setPlayer(player);
		Game game = new Game();
		game.setId(Long.parseLong(gameId));
		gameStaticalData.setGame(game);
		return gameStaticalData;
	}

	@JsonProperty("id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@JsonProperty("playerId")
	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	@JsonProperty("playerName")
	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	@JsonProperty("gameId")
	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	@JsonProperty("miss")
	public String getMiss() {
		return miss;
	}

	public void setMiss(String miss) {
		this.miss = miss;
	}

	@JsonProperty("score")
	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}
	
	@JsonProperty("victory")
	public String getVictory() {
		return victory;
	}

	public void setVictory(String victory) {
		this.victory = victory;
	}
}
