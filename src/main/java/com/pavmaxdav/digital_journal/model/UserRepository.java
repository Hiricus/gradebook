package com.pavmaxdav.digital_journal.model;

import com.pavmaxdav.digital_journal.enitiy.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findById(Integer id);
    Optional<User> findByLogin(String login);
}
