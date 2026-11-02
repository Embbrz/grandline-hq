package com.worldgovernment.grandline_hq.Service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.worldgovernment.grandline_hq.Entity.pirates;
import com.worldgovernment.grandline_hq.Exception.InvalidAmountException;
import com.worldgovernment.grandline_hq.Exception.PirateNotFoundException;
import com.worldgovernment.grandline_hq.Repository.piraterepository;
@Service
public class pirateService {
	
	private piraterepository piraterepo;
	
	@Autowired
	public pirateService(piraterepository pr) {
		this.piraterepo = pr;
	}
	
	
	public Page<pirates> getAll(Pageable pageable){
		return piraterepo.findAll(pageable);
	}
	
	public Page<pirates> getWanted(Pageable pageable){
		return piraterepo.findByStatus(pageable);
		
	}
	
	public pirates updateBounty(UUID id, Long amount) {
		if (amount < 0) {
			throw new InvalidAmountException("Sorry, Amount cannot be Lower than 0!");
		}
		pirates pirate = piraterepo.findById(id)
				.orElseThrow(() -> new PirateNotFoundException("Pirate with ID: "+ id +" Not Found!"));
		pirate.setCurrentBounty(pirate.getCurrentBounty() + amount);
		return piraterepo.save(pirate);
	}
	
	

}
