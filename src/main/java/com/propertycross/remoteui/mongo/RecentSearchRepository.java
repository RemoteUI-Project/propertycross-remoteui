package com.propertycross.remoteui.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecentSearchRepository extends MongoRepository<RecentSearch, String> {

	public List<RecentSearch> findAllByUserIdOrderByTimestampDesc(String userId);

}