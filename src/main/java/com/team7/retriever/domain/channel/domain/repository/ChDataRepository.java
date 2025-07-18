package com.team7.retriever.domain.channel.domain.repository;

import com.team7.retriever.domain.channel.domain.document.ChData;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChDataRepository extends MongoRepository<ChData, String> {

	// 채널 아이디로 조회
	List<ChData> findByChannelId(long channelId);

	// 메시지 내용으로 조회 (포함되는 것)
	@Query("{ 'text':  { $regex: ?0, $options: 'i' } }")
	List<ChData> findByText(String text);
}

