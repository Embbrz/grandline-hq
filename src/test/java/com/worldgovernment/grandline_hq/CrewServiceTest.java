package com.worldgovernment.grandline_hq;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.worldgovernment.grandline_hq.Entity.Crew;
import com.worldgovernment.grandline_hq.Entity.pirates;
import com.worldgovernment.grandline_hq.Repository.CrewRepository;
import com.worldgovernment.grandline_hq.Repository.piraterepository;
import com.worldgovernment.grandline_hq.Service.CrewService;
import com.worldgovernment.grandline_hq.Service.pirateService;

@ExtendWith(MockitoExtension.class)
public class CrewServiceTest {
	
	@Mock
	private pirateService pirateService;
	
	@Mock
	private CrewRepository crewRepo;
	
	@Mock
	private piraterepository pirateRepo;
	
	@InjectMocks
	private CrewService crewService;
	
	
	@Test
	public void testproccesscreatecrew_Success() {
		UUID crewID = UUID.randomUUID();
		UUID pirateID = UUID.randomUUID();
		
		pirates pirate = new pirates();
		pirate.setId(pirateID);
		pirate.setName("Luffy");
		Crew crew = new Crew();
		crew.setId(crewID);
		crew.setCaptainId(pirateID);
		crew.setName("StrawHat Pirates");
		pirate.setCurrentBounty(100L);
		
		when(pirateService.getPirate(any())).thenReturn(pirate);
		when(crewRepo.save(any())).thenReturn(crew);
		
		Crew mockCrew = crewService.createCrew(crew);
		
		assertEquals(pirateID, mockCrew.getCaptainId());
		assertEquals(100L, mockCrew.getTotalBounty());
		assertEquals("StrawHat Pirates", mockCrew.getName());
		assertEquals(mockCrew, pirate.getCrew());
	}
	
	
	@Test 
	public void testproccessrecruitPirate_Success() {
		UUID crewID = UUID.randomUUID();
		UUID pirateID = UUID.randomUUID();
		
		pirates pirate = new pirates();
		pirate.setId(pirateID);
		pirate.setName("Luffy");
		Crew crew = new Crew();
		crew.setId(crewID);
		crew.setCaptainId(pirateID);
		crew.setName("StrawHat Pirates");
		pirate.setCurrentBounty(100L);

		
		pirates pirate2 = new pirates();
		UUID pirate2ID = UUID.randomUUID();
		pirate2.setId(pirate2ID);
		pirate2.setName("Zoro");
		pirate2.setCurrentBounty(70L);
		
		when(crewRepo.findById(any())).thenReturn(Optional.of(crew));
		when(pirateService.getPirate(any())).thenReturn(pirate).thenReturn(pirate2);
		when(crewRepo.save(any())).thenReturn(crew);
		
		crewService.createCrew(crew);
		crewService.recruitPirate(crewID, pirate2ID);
		assertEquals(170L, crew.getTotalBounty());
		
		
	}
	@Test
	public void testproccessrecruitPirate_Success2() {
		UUID crewID = UUID.randomUUID();
		UUID pirateID = UUID.randomUUID();
		
		pirates pirate = new pirates();
		pirate.setId(pirateID);
		pirate.setName("Luffy");
		Crew crew = new Crew();
		crew.setId(crewID);
		crew.setCaptainId(pirateID);
		crew.setName("StrawHat Pirates");
		pirate.setCurrentBounty(100L);

		Crew crew2 = new Crew();
		UUID crew2ID = UUID.randomUUID();
		crew2.setId(crew2ID);
		pirates pirate3 = new pirates();
		UUID pirate3ID = UUID.randomUUID();
		pirate3.setId(pirate3ID);
		pirate3.setName("Luffy");
		crew2.setCaptainId(pirate3ID);
		crew2.setName("StrawHat Pirates");
		pirate3.setCurrentBounty(100L);

		pirates pirate2 = new pirates();
		UUID pirate2ID = UUID.randomUUID();
		pirate2.setId(pirate2ID);
		pirate2.setName("Zoro");
		pirate2.setCurrentBounty(70L);
		when(crewRepo.findById(crewID)).thenReturn(Optional.of(crew));
		when(crewRepo.findById(crew2ID)).thenReturn(Optional.of(crew2));
		
		when(pirateService.getPirate(pirateID)).thenReturn(pirate);
		when(pirateService.getPirate(pirate2ID)).thenReturn(pirate2);
		when(pirateService.getPirate(pirate3ID)).thenReturn(pirate3);
		when(crewRepo.save(crew)).thenReturn(crew);
		when(crewRepo.save(crew2)).thenReturn(crew2);
	
		
		crewService.createCrew(crew2);
		crewService.recruitPirate(crew2ID, pirate2ID);
		crewService.createCrew(crew);
		crewService.recruitPirate(crewID, pirate2ID);
		assertEquals(170L, crew.getTotalBounty());
		assertEquals(crew, pirate2.getCrew());
		assertEquals(100L, crew2.getTotalBounty());
	}
	
	@Test
	public void testproccessrecruitPirate_CrewNotFound() {
		
		
		when(crewRepo.findById(any())).thenReturn(Optional.empty());
		
		org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
			crewService.recruitPirate(UUID.randomUUID(), UUID.randomUUID());
		});
		
	}
	
	
	@Test
	public void testproccessrecruitpirate_PirateAlreadyInCrew() {
		UUID crewID = UUID.randomUUID();
		UUID pirateID = UUID.randomUUID();
		
		pirates pirate = new pirates();
		pirate.setId(pirateID);
		pirate.setName("Luffy");
		Crew crew = new Crew();
		crew.setId(crewID);
		crew.setCaptainId(pirateID);
		crew.setName("StrawHat Pirates");
		pirate.setCurrentBounty(100L);
		
		when(pirateService.getPirate(any())).thenReturn(pirate);
		when(crewRepo.save(any())).thenReturn(crew);
		
		crewService.createCrew(crew);
		
		org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
			crewService.recruitPirate(crewID, pirateID);
		});
		
	}
	
	@Test
	public void testproccessleaveCrew_SuccessCrewMember() {
		UUID crewID = UUID.randomUUID();
		UUID captainID = UUID.randomUUID();
		UUID pirateID = UUID.randomUUID();
		
		Crew crew = new Crew();
		crew.setId(crewID);
		crew.setName("StrawHat Pirates");
		crew.setCaptainId(captainID);
		
		pirates captain = new pirates();
		captain.setId(captainID);
		captain.setName("Monkey D LUFFY");
		captain.setCurrentBounty(100L);
		
		pirates pirate = new pirates();
		pirate.setId(pirateID);
		pirate.setName("Zoro");
		pirate.setCurrentBounty(70L);
		
		when(crewRepo.findById(any())).thenReturn(Optional.of(crew));
		when(pirateService.getPirate(pirateID)).thenReturn(pirate);
		when(pirateService.getPirate(captainID)).thenReturn(captain);
		when(crewRepo.save(any())).thenReturn(crew);
		
		crewService.createCrew(crew);
		crewService.recruitPirate(crewID, pirateID);
		
		crewService.leaveCrew(pirateID, crewID);
		
		assertEquals(100L, crew.getTotalBounty());
		assertEquals(null, pirate.getCrew());
		
	}
	
	@Test
	public void testproccessleaveCrew_SuccessCaptain() {
		UUID crewID = UUID.randomUUID();
		UUID captainID = UUID.randomUUID();
		UUID pirateID = UUID.randomUUID();
		
		Crew crew = new Crew();
		crew.setId(crewID);
		crew.setName("StrawHat Pirates");
		crew.setCaptainId(captainID);
		
		pirates captain = new pirates();
		captain.setId(captainID);
		captain.setName("Monkey D LUFFY");
		captain.setCurrentBounty(100L);
		
		pirates pirate = new pirates();
		pirate.setId(pirateID);
		pirate.setName("Zoro");
		pirate.setCurrentBounty(70L);
		
		UUID sanjiID = UUID.randomUUID();
		pirates sanji = new pirates();
		sanji.setId(sanjiID);
		sanji.setCurrentBounty(30L);
		sanji.setName("Vinsmoke Sanji");
		
		
		when(pirateService.getPirate(captainID)).thenReturn(captain);
		when(pirateService.getPirate(pirateID)).thenReturn(pirate);
		when(pirateService.getPirate(sanjiID)).thenReturn(sanji);
		when(pirateRepo.findByCrew(crewID)).thenReturn(List.of(captain, pirate, sanji));
		
		when(crewRepo.findById(any())).thenReturn(Optional.of(crew));
		
		when(crewRepo.save(any())).thenReturn(crew);
		
		crewService.createCrew(crew);
		crewService.recruitPirate(crewID, pirateID);
		crewService.recruitPirate(crewID, sanjiID);
		crewService.leaveCrew(captainID, crewID);
		assertEquals(null, captain.getCrew());
		assertEquals(pirateID, crew.getCaptainId());
		assertEquals(100L, crew.getTotalBounty());
		
		
	}
	
	@Test
	public void testproccessleaveCrew_SuccesCrewDeleted() {
		UUID crewID = UUID.randomUUID();
		UUID captainID = UUID.randomUUID();
		
		Crew crew = new Crew();
		crew.setId(crewID);
		crew.setName("StrawHats");
		crew.setCaptainId(captainID);
		
		pirates captain = new pirates();
		captain.setId(captainID);
		captain.setCurrentBounty(100L);
		captain.setName("Monkey D Luffy");
		
		when(pirateService.getPirate(any())).thenReturn(captain);
		when(crewRepo.findById(any())).thenReturn(Optional.of(crew));
		when(pirateRepo.findByCrew(crewID)).thenReturn(List.of(captain));
		
		crewService.createCrew(crew);
		crewService.leaveCrew(captainID, crewID);
		
		assertEquals(0L, crew.getTotalBounty());
		assertEquals(null, captain.getCrew());
	}
	
	@Test
	public void testproccessleaveCrew_FailureCrewNotFound() {
		UUID pirateID = UUID.randomUUID();
		pirates pirate = new pirates();
		pirate.setId(pirateID);
		pirate.setCurrentBounty(100L);
		pirate.setName("Luffy");
		
		when(pirateService.getPirate(any())).thenReturn(pirate);
		
		org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
			crewService.leaveCrew(pirateID, UUID.randomUUID());
		});
	}
	@Test
	public void testproccessleaveCrew_FailurePirateNotInCrew() {
		UUID pirateID = UUID.randomUUID();
		pirates pirate = new pirates();
		pirate.setId(pirateID);
		pirate.setCurrentBounty(100L);
		pirate.setName("Luffy");
	
		
		UUID crewID = UUID.randomUUID();
		Crew crew = new Crew();
		crew.setId(crewID);
		crew.setCaptainId(UUID.randomUUID());
		crew.setName("butboo");
		
		when(pirateService.getPirate(any())).thenReturn(pirate);
		when(crewRepo.findById(any())).thenReturn(Optional.of(crew));
		
		org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
			crewService.leaveCrew(pirateID, crewID);
		});
	
	}
	
	@Test
	public void testproccessleaveCrew_FailurePirateInOtherCrew() {
		UUID pirateID = UUID.randomUUID();
		pirates pirate = new pirates();
		pirate.setId(pirateID);
		pirate.setCurrentBounty(100L);
		pirate.setName("Luffy");
		
		UUID cID = UUID.randomUUID();
		Crew c = new Crew();
		c.setId(cID);
		c.setCaptainId(pirateID);
		pirate.setCrew(c);
	
		
		UUID crewID = UUID.randomUUID();
		Crew crew = new Crew();
		crew.setId(crewID);
		crew.setCaptainId(UUID.randomUUID());
		crew.setName("butboo");
		
		when(pirateService.getPirate(any())).thenReturn(pirate);
		when(crewRepo.findById(any())).thenReturn(Optional.of(crew));
		
		org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
			crewService.leaveCrew(pirateID, crewID);
		});
		
	}
	


}
