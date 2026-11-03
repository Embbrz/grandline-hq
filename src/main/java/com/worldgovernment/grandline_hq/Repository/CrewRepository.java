package com.worldgovernment.grandline_hq.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.worldgovernment.grandline_hq.Entity.Crew;

public interface CrewRepository extends JpaRepository<Crew, UUID>{

}
