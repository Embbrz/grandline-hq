package com.worldgovernment.grandline_hq.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.worldgovernment.grandline_hq.Entity.DevilFruit;

@Repository
public interface FruitRepository extends JpaRepository<DevilFruit, UUID>{
	

}
