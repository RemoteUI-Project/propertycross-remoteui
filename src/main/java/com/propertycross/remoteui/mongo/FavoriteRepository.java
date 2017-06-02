package com.propertycross.remoteui.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface FavoriteRepository extends MongoRepository<Favorite, String> {

	public List<Favorite> findAllByUserId(String userId);

}