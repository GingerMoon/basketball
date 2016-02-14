package basketball.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;
import org.hibernate.validator.constraints.NotEmpty;


@Entity
public class Player implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id = (long) -1;
	@NotEmpty
	private String username = "";
	@NotEmpty
	private String password = "";
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
	@JoinTable(name = "Player_Role",
    joinColumns = @JoinColumn(name = "player_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private List<Role> roles = new ArrayList<Role>();
	
	public String getIsAdmin() {
		List<Role> roles = getRoles();
		Iterator it1 = roles.iterator();
		while(it1.hasNext()) {
			Role role = (Role) it1.next();
			Iterator it2 = role.getPermissions().iterator();
			while(it2.hasNext()) {
				Permission permission = (Permission) it2.next();
				String str = permission.getPermission();
				if(str.contains("ADMIN")) {
					return "TRUE";
				}
			}
		}
		return "NO";
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
}