package com.team7.retriever.auth.jwt.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.team7.retriever.auth.redis.Token;

public interface TokenRepository extends CrudRepository<Token, String> {

	Optional<Token> findByRefreshToken(final String refreshToken);
}
