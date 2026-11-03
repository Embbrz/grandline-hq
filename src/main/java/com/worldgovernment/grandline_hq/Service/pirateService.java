package com.worldgovernment.grandline_hq.Service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.worldgovernment.grandline_hq.Entity.DevilFruit;
import com.worldgovernment.grandline_hq.Entity.pirates;
import com.worldgovernment.grandline_hq.Exception.InvalidAmountException;
import com.worldgovernment.grandline_hq.Exception.PirateNotFoundException;
import com.worldgovernment.grandline_hq.Repository.FruitRepository;
import com.worldgovernment.grandline_hq.Repository.piraterepository;

import jakarta.transaction.Transactional;
@Service
public class pirateService {
	
	private piraterepository piraterepo;
	private FruitRepository fruitRepo;
	private FruitService fruitService;
	
	@Autowired
	public pirateService(piraterepository pr, FruitRepository fr, FruitService fs) {
		this.fruitRepo = fr;
		this.piraterepo = pr;
		this.fruitService = fs;
		
	}
	
	public pirates getPirate(UUID id) {
		return piraterepo.findById(id)
				.orElseThrow(() -> new PirateNotFoundException("Sorry! Pirate Not Found!"));
	}
	
	@Transactional
	public pirates pirateEatFruit(UUID pirateID, UUID fruitID) {
		pirates pirate = this.getPirate(pirateID);
		DevilFruit  fruit = fruitService.getById(fruitID);
		
		if (!(pirate.getDevilfruit() == null)) {
			throw new InvalidAmountException("Sorry! Pirate Already Ate a Devil Fruit!");
		}
		if (fruit.getIsConsumed()) {
			throw new InvalidAmountException("Sorry! Fruit has Already Been Eaten by another Pirate!");
		}
		fruit.setIsConsumed(true);
		pirate.setDevilfruit(fruit);
		fruitRepo.save(fruit);
		return piraterepo.save(pirate);
	}
	
	public pirates addPirate(pirates pirate) {
		if (!(pirate.getDevilfruit() == null)) {
			if (pirate.getDevilfruit().getIsConsumed()) {
				throw new InvalidAmountException("Sorry! Devil Fruit is Already in Use!");
			}
			pirate.getDevilfruit().setIsConsumed(true);
			
		}
		
		if (pirate.getCurrentBounty() < 0) {
			throw new InvalidAmountException("Sorry! invalid Amount of Bounty!");
		}
		
		fruitRepo.save(pirate.getDevilfruit());
		return piraterepo.save(pirate);
		
	}
	
	
	public Page<pirates> getAll(Pageable pageable){
		return piraterepo.findAll(pageable);
	}
	
	public Page<pirates> getWanted(Pageable pageable){
		return piraterepo.findByStatus(pageable);
		
	}
	
	@Transactional
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
