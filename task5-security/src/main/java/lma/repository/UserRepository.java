package lma.repository;

import lma.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Repository;


public interface UserRepository extends JpaRepository<User, Long>{
    User getByUsername(String username);
}
