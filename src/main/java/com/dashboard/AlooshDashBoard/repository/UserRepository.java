package com.dashboard.AlooshDashBoard.repository;

import com.dashboard.AlooshDashBoard.entity.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
