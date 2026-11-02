package com.worldgovernment.grandline_hq.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.worldgovernment.grandline_hq.Entity.pirates;
import com.worldgovernment.grandline_hq.Service.pirateService;
@RestController
@RequestMapping("/api/pirates")
public class PirateController {
	
	private pirateService pirateService;
	
	@Autowired
	public PirateController(pirateService ps) {
		this.pirateService = ps;
	}
	
	@GetMapping
	public Page<pirates> getAll(@PageableDefault(page = 0, size = 10) Pageable pageable){
		return pirateService.getAll(pageable);
		
	}
	
	@GetMapping("/wanted")
	public Page<pirates> getAllWanted(@PageableDefault(page = 0, size = 10) Pageable pageable){
		return pirateService.getWanted(pageable);
	}

}
