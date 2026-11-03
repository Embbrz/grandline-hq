package com.worldgovernment.grandline_hq.Service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.worldgovernment.grandline_hq.Entity.Crew;
import com.worldgovernment.grandline_hq.Entity.pirates;
import com.worldgovernment.grandline_hq.Exception.BountyAlreadyClaimedException;
import com.worldgovernment.grandline_hq.Exception.InvalidAmountException;
import com.worldgovernment.grandline_hq.Exception.PirateNotFoundException;
import com.worldgovernment.grandline_hq.Repository.CrewRepository;
import com.worldgovernment.grandline_hq.Repository.piraterepository;

@Service
public class CrewService {
	
	private CrewRepository crewRepo;
	private piraterepository pirateRepo;
	private pirateService pirateService;
	
	@Autowired
	public CrewService(CrewRepository cr, piraterepository pr, pirateService ps) {
		this.crewRepo = cr;
		this.pirateRepo = pr;
		this.pirateService = ps;
	}
	public Page<Crew> getAll(Pageable pageable){
		return crewRepo.findAll(pageable);
	}
	
	public Crew createCrew(Crew crew) {
		UUID captainID = crew.getCaptainId();
		pirates captain = pirateRepo.findById(captainID)
				.orElseThrow(() -> new PirateNotFoundException("Captain is Not an Existing Pirate! "));
		crew.addTotalCrewBounty(captain.getCurrentBounty());
		return crewRepo.save(crew);
	}
	
	public Crew leaveCrew(UUID pirateID, UUID crewID) {
		pirates pirate = pirateService.getPirate(pirateID);
		Crew crew = this.getById(crewID);
		if (pirate.getCrew() == null) {
			throw new InvalidAmountException("Sorry! Pirate was Not Found in Crew!");
		}
		if (pirate.getCrew().getId() != crewID) {
			throw new BountyAlreadyClaimedException("Sorry! Pirate was Not Found in Crew!");
		}
		if (crew.getCaptainId() == pirateID) {
			crewRepo.delete(crew);
			return null;
		}
		crew.subtractTotalCrewBounty(pirate.getCurrentBounty());
		pirate.setCrew(null);
		pirateRepo.save(pirate);
		return crewRepo.save(crew);
	}
	
	
	public Crew getById(UUID crewID) {
		return crewRepo.findById(crewID)
				.orElseThrow(() -> new PirateNotFoundException("Crew Not Found!"));
	}
	
	public Crew recruitPirate(UUID crewID, UUID pirateID) {
		Crew crew = this.getById(crewID);
		pirates pirate = pirateService.getPirate(pirateID);
		
		if (pirate.getCrew().getId() == crewID) {
			throw new InvalidAmountException("Sorry! Pirate Already in Crew!");
		}
		
		if (!(pirate.getCrew() == null)) {
			this.leaveCrew(pirateID, crewID);
		}
		pirate.setCrew(crew);
		crew.addTotalCrewBounty(pirate.getCurrentBounty());
		
		
		return crewRepo.save(crew);
		
	}
	
	

}
