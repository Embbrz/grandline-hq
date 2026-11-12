package com.worldgovernment.grandline_hq.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.worldgovernment.grandline_hq.Entity.Crew;
import com.worldgovernment.grandline_hq.Entity.pirates;

import jakarta.persistence.LockModeType;

@Repository
public interface piraterepository extends JpaRepository<pirates, UUID>{
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("SELECT p FROM pirates p WHERE p.id = :id")
	Optional<pirates> findByIdWithLock(@Param("id") UUID id);
	
	
	@Query("SELECT p FROM pirates p WHERE p.status = 'WANTED'")
	Page<pirates> findByStatus(Pageable pageable);
	
	
	List<pirates> findByCrew(Crew crew);
	


}
