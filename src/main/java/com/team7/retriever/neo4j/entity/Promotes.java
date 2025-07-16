package com.team7.retriever.neo4j.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@RelationshipProperties
public class Promotes {

	@Id
	@GeneratedValue
	private Long id;

	@TargetNode
	private Channel channel;

	private Promotes(Channel channel) {
		this.channel = channel;
	}

	public static Promotes link(Channel channel) {
		return new Promotes(channel);
	}

}

