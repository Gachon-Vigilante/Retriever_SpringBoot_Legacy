package com.team7.retriever.global.auth.jwt.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.team7.retriever.global.auth.redis.Token;

public interface TokenRepository extends CrudRepository<Token, String> {

	Optional<Token> findByRefreshToken(final String refreshToken);
}
