package com.worldgovernment.grandline_hq;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.worldgovernment.grandline_hq.Entity.BountyClaim;
import com.worldgovernment.grandline_hq.Entity.BountyHunter;
import com.worldgovernment.grandline_hq.Entity.pirates;
import com.worldgovernment.grandline_hq.Repository.bountyClaimRepository;
import com.worldgovernment.grandline_hq.Repository.bountyhunterrepository;
import com.worldgovernment.grandline_hq.Repository.piraterepository;
import com.worldgovernment.grandline_hq.Service.BountyService;


@ExtendWith(MockitoExtension.class)
public class BountyServiceTest {
	
	@Mock
	private piraterepository piraterepo;
	
	@Mock
	private bountyhunterrepository bountyhunterrepo;
	
	@Mock
	private bountyClaimRepository bountyClaimRepo;
	
	@InjectMocks
	private BountyService bountyService;
	
	
	@Test
	void testProcessBountyClaim_Success() {
		UUID pirateId = UUID.randomUUID();
		UUID hunterID = UUID.randomUUID();
		pirates mockPirate = new pirates();
		mockPirate.setId(pirateId);
		mockPirate.setName("Monkey D. Luffy");
		mockPirate.setCurrentBounty(100L);

		
		
		BountyHunter mockHunter = new BountyHunter();
		mockHunter.setId(hunterID);
		mockHunter.setName("Pirate Hunter Zoro");
		when(piraterepo.save(any())).thenReturn(mockPirate);
		when(bountyhunterrepo.save(any())).thenReturn(mockHunter);
		when(piraterepo.findByIdWithLock(pirateId)).thenReturn(Optional.of(mockPirate));
		when(bountyClaimRepo.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
		BountyClaim claim = bountyService.processBountyClaim(pirateId, true, mockHunter);
		
	
		
		assertEquals(100L, claim.getRewardPaid());
		assertEquals(100L, mockHunter.getTotal_bounty_cash());
		assertEquals(1, mockHunter.getBountyClaimed());
		assertEquals("CAPTURED", mockPirate.getStatus());
		
	}
	
	@Test
	void testProcessBountyClaim_PirateNotFound() {
		UUID randomId = UUID.randomUUID();
		UUID hunterId = UUID.randomUUID();
		BountyHunter mockHunter = new BountyHunter();
		mockHunter.setId(hunterId);
		mockHunter.setName("Pirate Hunter Zoro");
		
		when(piraterepo.findByIdWithLock(randomId)).thenReturn(Optional.empty());
		
		org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
			bountyService.processBountyClaim(randomId, true, mockHunter);
		});
		
		
	}
	
	@Test
	void testProcessBountyClaim_AlreadyCapturedByAnotherHunter() {
		UUID pirateId = UUID.randomUUID();
		UUID zoroId = UUID.randomUUID();
		UUID sanjiId = UUID.randomUUID();
		
		pirates mockPirate = new pirates();
		mockPirate.setId(pirateId);
		mockPirate.setName("Monkey D Luffy");
		mockPirate.setCurrentBounty(100L);
		
		BountyHunter zoro = new BountyHunter();
		zoro.setId(zoroId);
		zoro.setName("Pirate Hunter Zoro");
		
		BountyHunter sanji = new BountyHunter();
		sanji.setId(sanjiId);
		sanji.setName("Black Leg Sanji");
		
		when(piraterepo.findByIdWithLock(pirateId)).thenReturn(Optional.of(mockPirate));
		when(piraterepo.save(any())).thenReturn(mockPirate);
		when(bountyhunterrepo.save(any())).thenReturn(zoro).thenReturn(sanji);
		
		bountyService.processBountyClaim(pirateId, true, zoro);
		
		
		org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, 
				() -> {
					bountyService.processBountyClaim(pirateId, true, sanji);
				});
	}

}
