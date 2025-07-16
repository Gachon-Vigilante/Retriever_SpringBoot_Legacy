package com.team7.retriever.neo4j.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

import org.springframework.data.neo4j.core.schema.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Node("Channel")
public class Channel {
	@Id
	private Long id;

	private String title;
	private String username;
	private String status;
	private int promotedCount;

	@Relationship(type = "SELLS", direction = Relationship.Direction.OUTGOING)
	@JsonIgnoreProperties("channels")
	private Set<Argot> sellsArgots = new HashSet<>();

}
