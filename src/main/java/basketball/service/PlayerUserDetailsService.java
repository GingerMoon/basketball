package basketball.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import basketball.domain.Permission;
import basketball.domain.Player;
import basketball.domain.PlayerUserDetails;
import basketball.domain.Role;


@Service
public class PlayerUserDetailsService implements UserDetailsService {

	@Autowired
	private PlayerService playerService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (username.isEmpty()) {
			throw new UsernameNotFoundException("Username was empty");
		}

		Player player = playerService.getPlayer(username);

		if (player == null) {
			throw new UsernameNotFoundException("Username not found");
		}

		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		if (!player.getRoles().isEmpty()) {
			for (Role role : player.getRoles()) {
				grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole()));
				for (Permission permission : role.getPermissions()) {
					grantedAuthorities.add(new SimpleGrantedAuthority(permission.getPermission()));
				}
			}
		}

		return new PlayerUserDetails(playerService.getPlayer(username), grantedAuthorities);
	}
	
	public PlayerUserDetails getCurrentPlayerUserDetails() {
		return (PlayerUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	
	public Player getCurrentPlayer() {
		return getCurrentPlayerUserDetails().getPlayer();
	}
	
	public boolean hasAuthority(String authStr) {
		for (GrantedAuthority authority : getCurrentPlayerUserDetails().getAuthorities()) {
			String str = authority.getAuthority();
			if(str.contains(authStr))
				return true;
		}
		return false;
	}
}
