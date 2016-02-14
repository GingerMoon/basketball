/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basketball.json.bean;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import basketball.domain.Player;

import com.fasterxml.jackson.annotation.JsonProperty;

@Component
public class PlayerJsonBean {
    private String id;
    private String username;
    private String password;
    
    public PlayerJsonBean() {}
    
    public PlayerJsonBean(Player player) {
    	this.id = new Long(player.getId()).toString();
    	this.username = player.getUsername();
    	this.password = player.getPassword();
    }

    public Player player() {
    	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    	Player player = new Player();
    	if(id != null) {
    		player.setId(Long.parseLong(id));
    	}
    	player.setUsername(username);
    	player.setPassword(passwordEncoder.encode(password));
    	return player; 
    }
    
	@JsonProperty("id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@JsonProperty("username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@JsonProperty("password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
