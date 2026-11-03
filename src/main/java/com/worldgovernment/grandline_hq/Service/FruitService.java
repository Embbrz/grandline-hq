package com.worldgovernment.grandline_hq.Service;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.worldgovernment.grandline_hq.Entity.DevilFruit;
import com.worldgovernment.grandline_hq.Exception.PirateNotFoundException;
import com.worldgovernment.grandline_hq.Repository.FruitRepository;
@Service
public class FruitService {
	
	private FruitRepository fruitRepo;
	
	@Autowired
	public FruitService(FruitRepository fr) {
		this.fruitRepo = fr;
	}
	
	
	
	public Page<DevilFruit> getAll(Pageable pageable){
		return fruitRepo.findAll(pageable);
		
	}
	
	public DevilFruit getById(UUID id) {
		return fruitRepo.findById(id)
				.orElseThrow(() -> new PirateNotFoundException("Devil Fruit Not Found!"));
	}

}
