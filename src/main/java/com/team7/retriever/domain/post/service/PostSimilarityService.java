package com.team7.retriever.domain.post.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostSimilarityService {

	private final RestTemplate restTemplate;

	@Value("${flask.url}")
	private String flaskUrl;

	public void calculateSimilarity() {
		log.info("[PostSimilarityService] API 호출");
		String api1 = flaskUrl + "/cluster/post_preprocess";
		String api2 = flaskUrl + "/cluster/post_similarity";
		String api3 = flaskUrl + "/cluster/post_cluster";

		ResponseEntity<Map> response = restTemplate.postForEntity(api1, null, Map.class);

		if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
			String message = response.getBody().get("message").toString();
			log.info("[PostSimilarityService] message: " + message);
			log.info("[PostSimilarityService] post_preprocess 성공 !");
		} else {
			log.info("[PostSimilarityService] post_preprocess 실패");
		}
		log.info("[PostSimilarityService] post_preprocess 실행 완료");

		ResponseEntity<Map> response2 = restTemplate.postForEntity(api2, null, Map.class);
		if (response2.getStatusCode().is2xxSuccessful() && response2.getBody() != null) {
			String message = response2.getBody().get("message").toString();
			log.info("[PostSimilarityService] message: " + message);
			log.info("[PostSimilarityService] post_similarity 성공 !");
		} else {
			log.info("[PostSimilarityService] post_similarity 실패");
		}
		log.info("[PostSimilarityService] post_similarity 실행 완료");

		ResponseEntity<Map> response3 = restTemplate.postForEntity(api3, null, Map.class);

		if (response3.getStatusCode().is2xxSuccessful() && response3.getBody() != null) {
			String message = response3.getBody().get("message").toString();
			int totla_document = Integer.parseInt(response3.getBody().get("total_documents").toString());
			int noise_document = Integer.parseInt(response3.getBody().get("noise_documents").toString());
			float silhouette_score = Float.parseFloat(response3.getBody().get("silhouette_score").toString());

			log.info("[PostSimilarityService] message: " + message);
			log.info("[PostSimilarityService] totla_document: " + totla_document);
			log.info("[PostSimilarityService] noise_document: " + noise_document);
			log.info("[PostSimilarityService] silhouette_score: " + silhouette_score);

			log.info("[PostSimilarityService] 성공 !");
		} else {
			log.info("[PostSimilarityService] 실패");
		}

		log.info("[PostSimilarityService] 실행 완료");

	}
}
