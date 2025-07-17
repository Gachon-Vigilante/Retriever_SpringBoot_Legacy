package com.team7.retriever.repository;

import com.team7.retriever.domain.crawling.domain.document.PostHtml;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostHtmlRepository extends MongoRepository<PostHtml, String> {
    boolean existsByUrl(String url);
}
