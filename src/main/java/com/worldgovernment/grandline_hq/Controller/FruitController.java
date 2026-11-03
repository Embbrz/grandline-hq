package com.worldgovernment.grandline_hq.Controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.worldgovernment.grandline_hq.Entity.DevilFruit;
import com.worldgovernment.grandline_hq.Service.FruitService;

@RestController
@RequestMapping("/devil-fruits")
public class FruitController {
	
	private FruitService fruitService;
	
	@Autowired
	public FruitController(FruitService fs) {
		this.fruitService = fs;
	}
	
	
	@GetMapping()
	public Page<DevilFruit> getAll(@PageableDefault(page = 0, size = 10) Pageable pageable){
		return fruitService.getAll(pageable);
		
	}
	
	@GetMapping("/{id}")
	public DevilFruit getFruit(@PathVariable UUID id) {
		return fruitService.getById(id);
	}
	
	
	

}
