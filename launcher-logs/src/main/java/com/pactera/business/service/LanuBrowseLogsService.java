package com.pactera.business.service;



import org.springframework.data.domain.Page;

import com.pactera.domain.LaunBrowseLogs;

public interface LanuBrowseLogsService {

	void addStoreBrowseLogs(LaunBrowseLogs storeBrowseLogs);

	Page<LaunBrowseLogs> findStoreBrowseLogs(String userName,String channelId,int page,int pageSize);
}
