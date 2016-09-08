package it.cabsb.persistence.service.impl;

import it.cabsb.persistence.dao.IRoleDao;
import it.cabsb.persistence.dao.IUserDao;
import it.cabsb.persistence.service.ISearchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service  
@Transactional(rollbackFor=Exception.class)
public class SearchService implements ISearchService {
	
	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private IRoleDao roleDao;
	
	public void reindex() {
		userDao.reindex();
		roleDao.reindex();
	}
}
