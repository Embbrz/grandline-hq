package com.worldgovernment.grandline_hq.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.worldgovernment.grandline_hq.Entity.BountyHunter;

public interface bountyhunterrepository extends JpaRepository<BountyHunter, UUID>{

}
