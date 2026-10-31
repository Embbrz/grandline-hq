package com.worldgovernment.grandline_hq.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.worldgovernment.grandline_hq.Repository.piraterepository;

@Service
public class pirateService {
	
	private piraterepository piraterepo;
	
	@Autowired
	public pirateService(piraterepository pr) {
		this.piraterepo = pr;
	}

}
