package com.team7.retriever.domain.channel.domain.repository;

import com.team7.retriever.domain.channel.domain.document.ChannelSimilarity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelSimilarityRepository extends MongoRepository<ChannelSimilarity, String> {
    ChannelSimilarity findByChannelId(long channelId);
}
