package com.worldgovernment.grandline_hq.Service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.worldgovernment.grandline_hq.Entity.MarineFleet;
import com.worldgovernment.grandline_hq.Exception.BountyAlreadyClaimedException;
import com.worldgovernment.grandline_hq.Exception.InvalidAmountException;
import com.worldgovernment.grandline_hq.Exception.PirateNotFoundException;
import com.worldgovernment.grandline_hq.Repository.MarineFleetRepository;

import jakarta.transaction.Transactional;

@Service
public class fleetService {
	
	private MarineFleetRepository fleetRepo;
	
	@Autowired
	public fleetService(MarineFleetRepository mfr) {
		this.fleetRepo = mfr;
	}
	public MarineFleet findFleet(UUID id) {
		MarineFleet marineFleet = fleetRepo.findById(id)
				.orElseThrow(() -> new PirateNotFoundException("Marine Fleet Not Found!"));
		return marineFleet;
	}

	public MarineFleet createFleet(MarineFleet marineFleet) {
		return fleetRepo.save(marineFleet);
	}
	
	@Transactional
	public MarineFleet triggerBusterCall(UUID fleetID) {
		MarineFleet fleet = this.findFleet(fleetID);
		
		if (fleet.getShipCount() < 10) {
			throw new InvalidAmountException("Sorry! Need Atleast 10 Marine Ships for Fleet!");
		}
		if (!(fleet.getStatus().equals("STANDBY"))) {
			throw new BountyAlreadyClaimedException("Sorry, Marine Fleet is Currently Busy!");
			
		}
		fleet.busterCallActive();
		return fleetRepo.save(fleet);
	}

}
