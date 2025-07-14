package com.team7.retriever.neo4j.repository;

import com.team7.retriever.neo4j.entity.Posts;

import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface NeoPostsRepository extends Neo4jRepository<Posts, String> {

	Optional<Posts> findByPostId(String postId);

	/*
	// paging
	@Query("""
		    MATCH (p:Post)
		    OPTIONAL MATCH (p)-[pr:PROMOTES]->(c:Channel)
		    OPTIONAL MATCH (p)-[sim:SIMILAR]->(sp:Post)
		    RETURN p, collect(pr), collect(c), collect(sim), collect(sp)
		    SKIP $skip LIMIT $limit
		""")
	List<Posts> findAllWithPromotesAndSimilarPaged(@Param("skip") long skip, @Param("limit") long limit);

	 */

	// stream
	@Query("""
		    MATCH (p:Post)
		    OPTIONAL MATCH (p)-[pr:PROMOTES]->(c:Channel)
		    OPTIONAL MATCH (p)-[sim:SIMILAR]->(sp:Post)
		    RETURN p, collect(pr), collect(c), collect(sim), collect(sp)
		""")
	Stream<Posts> streamAllWithPromotesAndSimilar();

	/*
	// 기존 // 데이터 많으면 실행 X
	@Query("""
		    MATCH (p:Post)
		    OPTIONAL MATCH (p)-[pr:PROMOTES]->(c:Channel)
		    OPTIONAL MATCH (p)-[sim:SIMILAR]->(sp:Post)
		    RETURN p, collect(pr), collect(c), collect(sim), collect(sp)
		""")
	List<Posts> findAllWithPromotesAndSimilar();

	 */

	// postId 설정
	@Query("MATCH (p:Post) WHERE p.content = $content SET p.postId = $postId RETURN p")
	Optional<Posts> updatePostIdByContent(@Param("content") String content, @Param("postId") String postId);
}
