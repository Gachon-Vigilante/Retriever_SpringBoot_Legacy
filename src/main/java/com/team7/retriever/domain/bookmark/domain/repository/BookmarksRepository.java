package com.team7.retriever.domain.bookmark.domain.repository;

import com.team7.retriever.domain.bookmark.domain.document.Bookmarks;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookmarksRepository extends MongoRepository<Bookmarks, String> {

}
