package com.team7.retriever.repository;

import com.team7.retriever.domain.drug.domain.document.Drugs;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DrugsRepository extends MongoRepository<Drugs, String> {

	// 전체 조회
	List<Drugs> findAll();

	// 아이디로 조회
	Optional<Drugs> findById(String id);

	// 이름으로 조회
	@Query("{ 'drugName':  { $regex:  ?0, $options: 'i' } }")
	List<Drugs> findByDrugName(String drugName);

	// 타입으로 조회
	@Query("{ 'drugType':  { $regex:  ?0, $options: 'i' } }")
	List<Drugs> findByDrugType(String drugType);
}

