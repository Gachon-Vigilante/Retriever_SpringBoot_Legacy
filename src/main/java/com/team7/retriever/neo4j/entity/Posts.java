package com.team7.retriever.neo4j.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.*;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Node("Post")
public class Posts {

	@Id
	private String postId;
	private Long channelId;
	private String content;
	private String link;
	private String siteName;
	private String createdAt;
	private String updatedAt;

	@Builder.Default
	@Relationship(type = "PROMOTES", direction = Relationship.Direction.OUTGOING)
	@JsonManagedReference
	private Set<Promotes> promotesChannels = new HashSet<>();

	@Relationship(type = "SIMILAR", direction = Relationship.Direction.OUTGOING)
	// @JsonIgnoreProperties("similarPosts") // 직렬화 X // (depth = 1)
	@JsonIgnoreProperties({"promotesChannels", "similarPosts"})
	private Set<Posts> similarPosts = new HashSet<>();

	//    @Relationship(type = "SIMILAR", direction = Relationship.Direction.OUTGOING)
	//    @JsonManagedReference
	//    private Set<Posts> similarPosts = new HashSet<>();

    /*
    @Builder.Default
    @Relationship(type = "PROMOTES", direction = Relationship.Direction.OUTGOING)
    private Set<Promotes> promotesChannels = new HashSet<>();

    @Relationship(type = "SIMILAR", direction = Relationship.Direction.OUTGOING)
    private Set<Posts> similarPosts = new HashSet<>();

     */

}
