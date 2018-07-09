package com.pactera.business.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.pactera.domain.LaunBrowseLogs;

@Repository
public interface LanuBrowseLogsRepository extends MongoRepository<LaunBrowseLogs, Long> {
	
	Page<LaunBrowseLogs> findByUserNameAndChannelId(String userName,String channelId, Pageable pageable);
	
	Page<LaunBrowseLogs> findByUserName(String userName, Pageable pageable);
	
	Page<LaunBrowseLogs> findByChannelId(String channelId, Pageable pageable);

}
