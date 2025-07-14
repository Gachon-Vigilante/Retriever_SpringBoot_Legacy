package com.team7.retriever.neo4j.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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

    /*
    @Relationship(type = "SELLS", direction = Relationship.Direction.INCOMING)
    @JsonBackReference
    private Set<Channel> sellingChannels = new HashSet<>();

     */

	@Relationship(type = "REFERS_TO", direction = Relationship.Direction.OUTGOING)
	// @JsonManagedReference
	@JsonIgnoreProperties("argots")
	private Set<Drug> refersDrugs = new HashSet<>();


    /*
    // @Relationship(type = "REFERS_TO", direction = Relationship.Direction.OUTGOING)
    @Relationship(type = "REFERS_TO")
    @JsonManagedReference
    private Set<Drug> refersToDrugs = new HashSet<>();

    @Relationship(type = "SELLS", direction = Relationship.Direction.INCOMING)
    private Set<Channel> soldByChannels = new HashSet<>();

     */
}
