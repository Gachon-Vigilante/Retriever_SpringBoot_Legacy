package com.team7.retriever.neo4j.repository;

import com.team7.retriever.neo4j.entity.Argot;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NeoArgotRepository extends Neo4jRepository<Argot, Long> {

	@Query("""
		    MATCH (a: Argot)
		    OPTIONAL MATCH (a)-[rf:REFERS_TO]->(d:Drug)
		    RETURN a, collect(rf), collect(d)
		""")
	List<Argot> findAllWithRefersTo();
}
