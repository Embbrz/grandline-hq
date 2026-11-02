package com.worldgovernment.grandline_hq.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.worldgovernment.grandline_hq.Entity.pirates;
import com.worldgovernment.grandline_hq.Repository.piraterepository;
@Service
public class pirateService {
	
	private piraterepository piraterepo;
	
	@Autowired
	public pirateService(piraterepository pr) {
		this.piraterepo = pr;
	}
	
	
	public Page<pirates> getAll(Pageable pageable){
		return piraterepo.findAll(pageable);
	}
	
	public Page<pirates> getWanted(Pageable pageable){
		return piraterepo.findByStatus(pageable);
		
	}

}
