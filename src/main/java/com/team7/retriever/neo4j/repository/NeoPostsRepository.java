package com.team7.retriever.neo4j.repository;

import com.team7.retriever.neo4j.entity.Post;

import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface NeoPostsRepository extends Neo4jRepository<Post, String> {

	Optional<Post> findByPostId(String postId);

	// stream
	@Query("""
		    MATCH (p:Post)
		    OPTIONAL MATCH (p)-[pr:PROMOTES]->(c:Channel)
		    OPTIONAL MATCH (p)-[sim:SIMILAR]->(sp:Post)
		    RETURN p, collect(pr), collect(c), collect(sim), collect(sp)
		""")
	Stream<Post> streamAllWithPromotesAndSimilar();

	// postId 업데이트
	@Query("""
		MATCH (p:Post)
		WHERE p.content = $content AND p.link = $link AND p.postId IS NULL
		SET p.postId = $postId
		RETURN COUNT(p)
	""")
	int updatePostIdByContentAndLink(String content, String link, String postId);


}
