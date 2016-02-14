package basketball.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import basketball.domain.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findById(Long id);

}
