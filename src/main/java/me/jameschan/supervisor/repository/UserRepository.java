package me.jameschan.supervisor.repository;

import me.jameschan.supervisor.model.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @NotNull
    Optional<User> findByEmail(String email);

    @NotNull
    Optional<User> findByUsername(String username);
}
