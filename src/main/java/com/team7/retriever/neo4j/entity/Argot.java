package com.team7.retriever.neo4j.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@Node("Argot")
@Getter
@Setter
public class Argot {
	@Id
	private Long identity;

	private String name;
	private String drugId;

	@Relationship(type = "REFERS_TO", direction = Relationship.Direction.OUTGOING)
	@JsonIgnoreProperties("argots")
	private Set<Drug> refersDrugs = new HashSet<>();

}
