package com.pactera.business.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pactera.business.repository.LanuBrowseLogsRepository;
import com.pactera.business.service.LanuBrowseLogsService;
import com.pactera.domain.LaunBrowseLogs;
import com.pactera.utlis.HStringUtlis;

@Service
@Transactional
public class LanuBrowseLogsServiceImpl implements LanuBrowseLogsService {
	
	@Autowired
	private LanuBrowseLogsRepository storeBrowseLogsRepository;
	
	@Override
	public void addStoreBrowseLogs(LaunBrowseLogs storeBrowseLogs) {
		storeBrowseLogsRepository.save(storeBrowseLogs);
	}

	@Override
	public Page<LaunBrowseLogs> findStoreBrowseLogs(String userName,String channelId, int page, int pageSize) {
		page=page>0?page-1:0;
		PageRequest pageable = new PageRequest(page,pageSize,new Sort(Direction.DESC, "createDate"));
		Page<LaunBrowseLogs> storeBrowseLogsPage = null;
		if(HStringUtlis.isNotBlank(userName) && HStringUtlis.isNotBlank(channelId)){
			storeBrowseLogsPage=storeBrowseLogsRepository.findByUserName(userName, pageable);
		}else if(HStringUtlis.isNotBlank(channelId)){
			storeBrowseLogsPage=storeBrowseLogsRepository.findByChannelId(channelId, pageable);
		}else if(HStringUtlis.isNotBlank(userName)){
			storeBrowseLogsPage=storeBrowseLogsRepository.findByUserName(userName, pageable);
		}else{
			storeBrowseLogsPage=storeBrowseLogsRepository.findAll(pageable);
		}
		return storeBrowseLogsPage;
	}
}
