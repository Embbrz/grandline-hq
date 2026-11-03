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
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@GetMapping("{id}")
	public pirates getPirate(@PathVariable UUID id) {
		return pirateService.getPirate(id);
	}
	
	@PostMapping("/add")
	public pirates addPirate(@RequestBody pirates pirate) {
		return pirateService.addPirate(pirate);
	}
	
	@GetMapping
	public Page<pirates> getAll(@PageableDefault(page = 0, size = 10) Pageable pageable){
		return pirateService.getAll(pageable);
		
	}
	
	@GetMapping("/wanted")
	public Page<pirates> getAllWanted(@PageableDefault(page = 0, size = 10) Pageable pageable){
		return pirateService.getWanted(pageable);
	}
	
	
	@PutMapping("/{id}/bounty")
	public pirates updateBounty(@PathVariable UUID id, @RequestParam Long amount) {
		return pirateService.updateBounty(id, amount);
		
	}
	
	
	@PutMapping("{pirateID}/devil-fruit/{fruitID}")
	public pirates pirateEatFruit(@PathVariable UUID pirateID, @PathVariable  UUID fruitID) {
		return pirateService.pirateEatFruit(pirateID, fruitID);
	}

}
