package com.worldgovernment.grandline_hq.Controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.worldgovernment.grandline_hq.Entity.BountyClaim;
import com.worldgovernment.grandline_hq.Entity.BountyHunter;
import com.worldgovernment.grandline_hq.Service.BountyService;

@RestController
@RequestMapping("/bounties")
public class BountyClaimController {
	
	private BountyService bountyService;
	
	@Autowired
	public BountyClaimController(BountyService b) {
		this.bountyService = b;
		
	}
	
	@PostMapping("/claim")
	public BountyClaim bountClaim(@RequestParam UUID pirateID, @RequestBody BountyHunter bountyHunter) {
		return bountyService.processBountyClaim(pirateID, bountyHunter);
	}

}
