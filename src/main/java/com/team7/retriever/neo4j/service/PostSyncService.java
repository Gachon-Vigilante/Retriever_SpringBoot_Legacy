package com.team7.retriever.neo4j.service;

import com.team7.retriever.domain.post.domain.repository.PostsRepository;
import com.team7.retriever.domain.post.domain.document.Posts;
import com.team7.retriever.neo4j.repository.NeoPostsRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostSyncService {

	private final PostsRepository postsRepository;
	private final NeoPostsRepository neoPostRepository;

	public void syncPostIdsFromMongoToNeo4j() {
		List<Posts> mongoPosts = postsRepository.findAll();
		int successCount = 0;

		for (Posts mongoPost : mongoPosts) {
			String content = mongoPost.getContent();
			String link = mongoPost.getLink();
			String postId = mongoPost.getId();

			if (content != null && link != null && postId != null) {
				try {
					int updated = neoPostRepository.updatePostIdByContentAndLink(content, link, postId);

					if (updated > 0) {
						log.info("✅ 성공: postId=" + postId + " | link=" + link);
						successCount++;
					} else {
						log.info("⚠️ 대상 없음 (혹은 이미 있음): postId=" + postId + " | link=" + link);
					}
				} catch (Exception e) {
					log.info("❌ 실패: postId=" + postId + " | link=" + link);
					e.printStackTrace();
				}
			}
		}

		log.info("✅ postId 업데이트 완료: " + successCount + "건");
	}

}
