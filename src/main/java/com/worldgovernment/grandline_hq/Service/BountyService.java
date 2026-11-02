package com.worldgovernment.grandline_hq.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.worldgovernment.grandline_hq.Entity.BountyClaim;
import com.worldgovernment.grandline_hq.Entity.BountyHunter;
import com.worldgovernment.grandline_hq.Entity.pirates;
import com.worldgovernment.grandline_hq.Exception.BountyAlreadyClaimedException;
import com.worldgovernment.grandline_hq.Exception.PirateNotFoundException;
import com.worldgovernment.grandline_hq.Repository.bountyClaimRepository;
import com.worldgovernment.grandline_hq.Repository.bountyhunterrepository;
import com.worldgovernment.grandline_hq.Repository.piraterepository;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
@Service
public class BountyService {
	
	private piraterepository piraterepo;
	private bountyhunterrepository bountyhunterrepo;
	private bountyClaimRepository bountyClaimRepo;

	
	@Autowired
	public BountyService(piraterepository pr, bountyhunterrepository bh, bountyClaimRepository br) {
		this.piraterepo = pr;
		this.bountyhunterrepo = bh;
		this.bountyClaimRepo = br;
	
	}
	
	public Page<BountyClaim> getAllClaims(Pageable pageable){
		return bountyClaimRepo.findAll(pageable);
	}
	
	@Transactional
	public BountyClaim processBountyClaim(UUID pirateID, boolean isAlive,BountyHunter bountyHunter) {
		pirates pirate = piraterepo.findByIdWithLock(pirateID)
				.orElseThrow(() -> new PirateNotFoundException("Sorry! Pirate with the ID " + pirateID +" does not Exist!"));
		
		if (!(pirate.getStatus().equals("WANTED"))) {
			throw new BountyAlreadyClaimedException("Pirate is No Longer Wanted! Status: " + pirate.getStatus());
		}
		
		BountyClaim bountyClaim = new BountyClaim();
		bountyClaim.setBountyHunter(bountyHunter);
		bountyClaim.setPirate(pirate);
		bountyClaim.calculateReward();
		bountyClaim.setPirateIsAlive(isAlive);
		if (bountyClaim.getPirateIsAlive()) {
			pirate.capture();
		}
		else {
			pirate.defeated();
		}
		bountyHunter.addBountyCash(bountyClaim.getRewardPaid());
		
	
		
		piraterepo.save(pirate);
		bountyhunterrepo.save(bountyHunter);
		
		
		return bountyClaimRepo.save(bountyClaim);
		
		
		
	}

}
