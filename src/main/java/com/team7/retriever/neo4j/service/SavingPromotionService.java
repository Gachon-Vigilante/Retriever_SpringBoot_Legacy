package com.team7.retriever.neo4j.service;

import com.team7.retriever.neo4j.repository.NeoChannelRepository;
import com.team7.retriever.neo4j.repository.NeoPostsRepository;
import com.team7.retriever.neo4j.entity.Channel;
import com.team7.retriever.neo4j.entity.Promotes;
import com.team7.retriever.neo4j.entity.Post;
import com.team7.retriever.entity.Posts;
import com.team7.retriever.service.PostsService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SavingPromotionService {

	private final NeoChannelRepository neoChannelRepository;
	private final NeoPostsRepository neoPostsRepository;
	private final PostsService postsService;

	@Transactional
	public void createPromotionRelation(Long channelId, String postId) {
		log.info("[Neo4j Service] channelId: " + channelId);
		log.info("[Neo4j Service] postId: " + postId);
		// 채널 노드 조회 or 생성
		Channel channel = neoChannelRepository.findById(channelId)
			.orElseGet(() -> {
				Channel newChannel = new Channel();
				newChannel.setId(channelId);
				return neoChannelRepository.save(newChannel);
			});

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

		Optional<Posts> mongoPostOpt = postsService.getPostById(postId);
		if (mongoPostOpt.isEmpty()) {
			log.error("[Neo4j Service] MongoDB에서 해당 Post(" + postId + ")를 찾을 수 없습니다.");
			return;
		}

		Posts mongoPost = mongoPostOpt.get();

		// 포스트 노드 조회 or 생성
		Post post = neoPostsRepository.findByPostId(postId)
			.orElseGet(() -> {
				Post newPost = Post.builder()
					.postId(postId)
					.channelId(channelId)
					.content(mongoPost.getContent())
					.link(mongoPost.getLink())
					.siteName(mongoPost.getSource() != null ? mongoPost.getSource() : mongoPost.getSiteName())
					.createdAt(mongoPost.getCreatedAt().format(formatter))
					.updatedAt(mongoPost.getUpdatedAt().format(formatter))
					.build();
				log.info("[Neo4j Service] Neo4j에 Post 데이터 생성: postId = " + postId);
				return neoPostsRepository.save(newPost);
			});

		// 관계 중복 검사
		boolean alreadyPromoted = post.getPromotesChannels().stream()
			.anyMatch(promotes -> promotes.getChannel().getId().equals(channelId));

		if (!alreadyPromoted) {
			Promotes promotes = Promotes.link(channel);
			post.getPromotesChannels().add(promotes);
			neoPostsRepository.save(post);  // Post가 관계 주체
			log.info("[Neo4j Service] 홍보 관계 생성 완료");
		} else {
			log.info("[Neo4j Service] 이미 관계가 존재합니다");
		}

        /*
        // 관계 중복 방지 - 관계가 없으면 추가
        boolean alreadyPromoted = channel.getPromotedPosts().stream()
                .anyMatch(promotes -> promotes.getPost().getPostId().equals(postId));

        if (!alreadyPromoted) {
            Promotes promotes = new Promotes(post);
            channel.getPromotedPosts().add(promotes);
            neoChannelRepository.save(channel);  // 관계도 같이 저장됨
            System.out.println("[Neo4j Service] 홍보 관계 생성 완료");
            log.info("[Neo4j Service] 홍보 관계 생성 완료");
        } else {
            System.out.println("[Neo4j Service] 이미 관계가 존재합니다");
            log.info("[Neo4j Service] 이미 관계가 존재합니다");
        }

         */
	}

}
