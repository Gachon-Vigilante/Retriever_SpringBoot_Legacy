package com.team7.retriever.neo4j.repository;

import com.team7.retriever.neo4j.entity.Drug;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NeoDrugRepository extends Neo4jRepository<Drug, String> {
}
