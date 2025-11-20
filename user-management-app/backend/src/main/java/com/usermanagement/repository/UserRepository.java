package com.usermanagement.repository;

import com.usermanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<User> findByMobileNumber(String mobileNumber);
    Optional<User> findByUsernameAndMobileNumber(String username, String mobileNumber);
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
}

