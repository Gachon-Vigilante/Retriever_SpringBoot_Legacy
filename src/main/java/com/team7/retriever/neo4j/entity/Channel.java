package com.team7.retriever.neo4j.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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

    /*
    @Relationship(type = "PROMOTES", direction = Relationship.Direction.INCOMING)
    @JsonBackReference
    private Set<Promotes> promotedPosts = new HashSet<>();

     */

	@Relationship(type = "SELLS", direction = Relationship.Direction.OUTGOING)
	// @JsonManagedReference
	@JsonIgnoreProperties("channels")
	private Set<Argot> sellsArgots = new HashSet<>();

    /*
    @Relationship(type = "PROMOTES", direction = Relationship.Direction.INCOMING)
    private Set<Promotes> promotedPosts = new HashSet<>();

    @Relationship(type = "SELLS", direction = Relationship.Direction.OUTGOING)
    private Set<Argot> sellsArgots = new HashSet<>();

     */

}
