package com.worldgovernment.grandline_hq.Service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.worldgovernment.grandline_hq.Entity.BountyClaim;
import com.worldgovernment.grandline_hq.Entity.BountyHunter;
import com.worldgovernment.grandline_hq.Entity.pirates;
import com.worldgovernment.grandline_hq.Repository.bountyhunterrepository;
import com.worldgovernment.grandline_hq.Repository.piraterepository;

import jakarta.transaction.Transactional;

@Service
public class BountyService {
	
	private piraterepository piraterepo;
	private bountyhunterrepository bountyhunterrepo;

	
	@Autowired
	public BountyService(piraterepository pr, bountyhunterrepository bh) {
		this.piraterepo = pr;
		this.bountyhunterrepo = bh;
	
	}
	
	@Transactional
	public BountyClaim processBountyClaim(UUID pirateID, BountyHunter bountyHunter) {
		pirates pirate = piraterepo.findByIdWithLock(pirateID)
				.orElseThrow(() -> new RuntimeException("Sorry! Pirate Is not Eligible to Claim Bounty!"));
		
		if (!(pirate.getStatus().equals("WANTED"))) {
			throw new RuntimeException("Pirate is No Longer Wanted!");
		}
		BountyClaim bountyClaim = new BountyClaim();
		bountyClaim.setBountyHunter(bountyHunter);
		bountyClaim.setPirate(pirate);
		bountyClaim.calculateReward();
		bountyHunter.addBountyCash(bountyClaim.getRewardPaid());
		if (bountyClaim.getPirateIsAlive()) {
			pirate.capture();
		}
		else {
			pirate.defeated();
		}
		
		piraterepo.save(pirate);
		bountyhunterrepo.save(bountyHunter);
		
		return bountyClaim;
		
		
		
	}

}
