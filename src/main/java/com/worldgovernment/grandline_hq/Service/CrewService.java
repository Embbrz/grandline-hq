package com.worldgovernment.grandline_hq.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.worldgovernment.grandline_hq.Entity.Crew;
import com.worldgovernment.grandline_hq.Entity.pirates;
import com.worldgovernment.grandline_hq.Exception.BountyAlreadyClaimedException;
import com.worldgovernment.grandline_hq.Exception.InvalidAmountException;
import com.worldgovernment.grandline_hq.Exception.PirateNotFoundException;
import com.worldgovernment.grandline_hq.Repository.CrewRepository;
import com.worldgovernment.grandline_hq.Repository.piraterepository;

import jakarta.transaction.Transactional;

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
		pirates captain = pirateService.getPirate(captainID);
		captain.setCrew(crew);
		pirateRepo.save(captain);
		crew.addTotalCrewBounty(captain.getCurrentBounty());
		return crewRepo.save(crew);
	}
	
	@Transactional
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
			List<pirates> crewMates = pirateRepo.findByCrew(crew);
			if (crewMates.size() <= 1) {
				crew.subtractTotalCrewBounty(pirate.getCurrentBounty());
				crewRepo.delete(crew);
				pirate.setCrew(null);
				pirateRepo.save(pirate);
				return crew;
			}
			pirates newCaptain = crewMates.stream()
					.filter(p -> !p.getId().equals(pirateID))
					.max(Comparator.comparing(pirates::getCurrentBounty)).orElse(null);
			crew.setCaptainId(newCaptain.getId());
			
			
			

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
	
	@Transactional
	public Crew recruitPirate(UUID crewID, UUID pirateID) {
		Crew crew = this.getById(crewID);
		pirates pirate = pirateService.getPirate(pirateID);
	
		if (!(pirate.getCrew() == null)) {
			if (pirate.getCrew().getId() == crewID) {
				throw new InvalidAmountException("Sorry! Pirate Already in Crew!");
			}
			this.leaveCrew(pirateID, pirate.getCrew().getId());
			
		}
		
	
		
		
		pirate.setCrew(crew);
		pirateRepo.save(pirate);
		crew.addTotalCrewBounty(pirate.getCurrentBounty());
		
		
		return crewRepo.save(crew);
		
	}
	
	

}
