package com.worldgovernment.grandline_hq.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.worldgovernment.grandline_hq.Entity.MarineFleet;

@Repository
public interface MarineFleetRepository extends JpaRepository<MarineFleet, UUID>{
	
	List<MarineFleet> findByAssignedSea(String assignedSea);

}
