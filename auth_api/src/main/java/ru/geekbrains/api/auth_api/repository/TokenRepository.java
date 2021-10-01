package ru.geekbrains.api.auth_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.api.auth_api.model.Token;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
}
