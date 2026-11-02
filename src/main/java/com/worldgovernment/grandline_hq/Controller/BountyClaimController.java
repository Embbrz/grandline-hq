package com.worldgovernment.grandline_hq.Controller;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.worldgovernment.grandline_hq.Entity.BountyClaim;
import com.worldgovernment.grandline_hq.Entity.BountyHunter;
import com.worldgovernment.grandline_hq.Service.BountyService;
import org.springframework.data.domain.Page;
@RestController
@RequestMapping("/bounties")
public class BountyClaimController {
	
	private BountyService bountyService;
	
	@Autowired
	public BountyClaimController(BountyService b) {
		this.bountyService = b;
		
	}
	
	@GetMapping("/claims/history")
	public Page<BountyClaim> getAllClaims(@PageableDefault(page = 0, size = 10) Pageable pageable){
		return bountyService.getAllClaims(pageable);
		
	}
	
	@PostMapping("/claim")
	public BountyClaim bountClaim(@RequestParam UUID pirateID, 
			 @RequestParam(defaultValue = "true") boolean isAlive, @RequestBody BountyHunter bountyHunter) {
		return bountyService.processBountyClaim(pirateID, isAlive, bountyHunter);
	}

}
