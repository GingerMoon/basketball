package basketball.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class PlayerUserDetails implements UserDetails {

	private Player player;
	private List<? extends GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

	public PlayerUserDetails(Player player, List<? extends GrantedAuthority> authorities) {
		this.player = player;
		this.authorities = authorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return player.getPassword();
	}

	@Override
	public String getUsername() {
		return player.getUsername();
	}

	public Player getPlayer() {
		return player;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
