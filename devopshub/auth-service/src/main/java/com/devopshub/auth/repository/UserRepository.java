package com.devopshub.auth.repository;

import com.devopshub.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository Layer: Spring Data JPA repository for the users table in PostgreSQL devopshub_auth.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Caption: Query User by Username
     * Finds a single user entity matching the given unique username handle.
     *
     * @param username The username to look up
     * @return Optional containing the User if present, otherwise empty
     */
    Optional<User> findByUsername(String username);

    /**
     * Caption: Check Username Uniqueness
     * Fast existence query used during registration to prevent duplicate username collisions.
     *
     * @param username The username to check
     * @return true if the username is already registered, false otherwise
     */
    boolean existsByUsername(String username);
}
