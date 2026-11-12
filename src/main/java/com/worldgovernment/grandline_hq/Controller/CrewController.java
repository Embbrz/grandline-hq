package com.worldgovernment.grandline_hq.Controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.worldgovernment.grandline_hq.Entity.Crew;
import com.worldgovernment.grandline_hq.Service.CrewService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/crews")
public class CrewController {
	
	private CrewService crewService;
	
	
	@Autowired
	public CrewController(CrewService cs) {
		this.crewService = cs;
	}
	
	@PostMapping("/create")
	public Crew createCrew(@Valid @RequestBody Crew crew) {
		return crewService.createCrew(crew);
	}
	
	
	@GetMapping("/{crewID}")
	public Crew getCrew(@PathVariable UUID crewID) {
		return crewService.getById(crewID);
	}
	
	@GetMapping()
	public Page<Crew> getAll(@PageableDefault(page = 0, size = 10) Pageable pageable){
		return crewService.getAll(pageable);
		
	}
	@PutMapping("/crew/{crewID}/addPirate/{pirateID}")
	public Crew addPirateToCrew(@PathVariable UUID crewID, @PathVariable UUID pirateID) {
		return crewService.recruitPirate(crewID, pirateID);
		
	}
	

}
