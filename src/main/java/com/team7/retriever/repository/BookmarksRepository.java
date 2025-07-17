package com.team7.retriever.repository;

import com.team7.retriever.entity.Bookmarks;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarksRepository extends MongoRepository<Bookmarks, String> {

}
