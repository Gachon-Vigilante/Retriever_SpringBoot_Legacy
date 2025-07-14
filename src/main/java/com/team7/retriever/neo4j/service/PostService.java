package com.team7.retriever.neo4j.service;

import com.team7.retriever.neo4j.entity.Posts;
import com.team7.retriever.neo4j.repository.NeoPostsRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

	private final NeoPostsRepository postsRepository;

    /*
    public List<Posts> getAllPosts() {
        Iterable<Posts> iterable = postsRepository.findAll();
        List<Posts> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }

    public List<Posts> getAllPostsDepth1() {
        return postsRepository.findAllWithPromotesAndSimilar();
    }
    */


    /*
    // paging
    public List<Posts> getPagedPosts(int page, int size) {
        long skip = (long) page * size;
        return postsRepository.findAllWithPromotesAndSimilarPaged(skip, size);
    }
     */

	// stream
	public List<Posts> getAllPostsStreamed() {
		try (var stream = postsRepository.streamAllWithPromotesAndSimilar()) {
			return stream.collect(Collectors.toList());
		}
	}

}
