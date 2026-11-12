package com.worldgovernment.grandline_hq.Controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.worldgovernment.grandline_hq.Entity.MarineFleet;
import com.worldgovernment.grandline_hq.Service.fleetService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/marine-fleet")
public class fleetController {
	
	private fleetService fleetService;
	
	@Autowired
	public fleetController(fleetService fs) {
		this.fleetService = fs;
	}
	
	@PostMapping("/create")
	public MarineFleet createFleet(@Valid @RequestBody MarineFleet marineFleet) {
		return fleetService.createFleet(marineFleet);
	}
	
	@PutMapping("/{id}/buster-CALL")
	public MarineFleet busterCall(@PathVariable UUID id) {
		return fleetService.triggerBusterCall(id);
	}

}
