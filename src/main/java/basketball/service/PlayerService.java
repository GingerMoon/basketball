package basketball.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import basketball.domain.Player;
import basketball.domain.Role;
import basketball.json.bean.PlayerJsonBean;
import basketball.respository.PlayerRepository;
import basketball.respository.RoleRepository;


@Service
@Transactional(readOnly = true)
public class PlayerService {
	
	@Autowired
	private PlayerRepository playerRepository;

	@Autowired
	private RoleRepository roleRepository;
	
	@Transactional(readOnly = false)
	public Player save(Player player) {
		if(player.getRoles().isEmpty()) {
			Role role = roleRepository.findById((long) 2);
			player.getRoles().add(role);
		}
		return this.playerRepository.save(player);
	}

	@Transactional(readOnly = false)
	public Player update(Player player) {
		Player playerOriginal = this.playerRepository.findById(player.getId());
		if(player.getRoles().isEmpty()) {
			player.setRoles(playerOriginal.getRoles());
		}
		return this.playerRepository.save(player);
	}

	private List<PlayerJsonBean> playerEntities2json(List<Player> players) {
		List<PlayerJsonBean> player_jsn = new ArrayList<PlayerJsonBean>();
		for(Player player : players) {
			PlayerJsonBean itemjsn = new PlayerJsonBean(player);
			player_jsn.add(itemjsn);
		}
		return player_jsn;
	}
	
	public Player getPlayer(String username) {
		return this.playerRepository.findByUsername(username);
	}

	public List<PlayerJsonBean> find(PageRequest pageRequest) {
		Page<Player> players = this.playerRepository.findAll(pageRequest);
		return playerEntities2json(players.getContent());
	}

	public long count() {
		return this.playerRepository.count();
	}

	public Player findById(Long id) {
		return this.playerRepository.findById(id);
	}

	@Transactional(readOnly = false)
	public void deleteById(long id) {
		this.playerRepository.delete(id);
	}
	
}
