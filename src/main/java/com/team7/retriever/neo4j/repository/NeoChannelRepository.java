package com.team7.retriever.neo4j.repository;

import com.team7.retriever.neo4j.entity.Channel;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NeoChannelRepository extends Neo4jRepository<Channel, Long> {

	Optional<Channel> findById(Long channelId);

	@Query("""
		    MATCH (c:Channel)
		    OPTIONAL MATCH (c)-[sell:SELLS]->(a:Argot)
		    RETURN c, collect(sell), collect(a)
		""")
	List<Channel> findAllWithSells();

}

