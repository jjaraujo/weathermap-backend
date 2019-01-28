package com.hbsis.teste.domain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.hbsis.teste.domain.model.City;


public interface CityRepository extends MongoRepository<City, Long> {

	
	
}
