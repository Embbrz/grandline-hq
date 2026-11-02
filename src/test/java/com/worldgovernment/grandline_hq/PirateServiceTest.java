package com.worldgovernment.grandline_hq;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.worldgovernment.grandline_hq.Entity.pirates;
import com.worldgovernment.grandline_hq.Repository.piraterepository;
import com.worldgovernment.grandline_hq.Service.pirateService;

@ExtendWith(MockitoExtension.class)
public class PirateServiceTest {
	@Mock
	private piraterepository pirateRepo;
	@InjectMocks
	private pirateService pirateService;
	
	
	@Test
	void testproccesUpdateBounty_Success() {
		UUID id = UUID.randomUUID();
		
		pirates mockPirate = new pirates();
		mockPirate.setId(id);
		mockPirate.setName("Luffy");
		mockPirate.setCurrentBounty(100L);
		when(pirateRepo.findById(any())).thenReturn(Optional.of(mockPirate));
		when(pirateRepo.save(any())).thenReturn(mockPirate);
		
		pirates pirate = pirateService.updateBounty(id, 100L);
		
		assertEquals(mockPirate.getCurrentBounty(), 200L);
		
	}
	
	
	@Test
	void testprocessUpdateBounty_PirateNotFound() {
		
		UUID id = UUID.randomUUID();
		
		when(pirateRepo.findById(any())).thenReturn(Optional.empty());
		
		org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
			pirates pirate = pirateService.updateBounty(id, 100L);
		});
		
		
	}
	
	@Test
	void testprocessUpdateBounty_InvalidAmount() {
		UUID id = UUID.randomUUID();
		
		pirates mockPirate = new pirates();
		mockPirate.setId(id);
		mockPirate.setName("Luffy");
		mockPirate.setCurrentBounty(100L);
		
		org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
			pirates pirate = pirateService.updateBounty(id, -60L);
		});
	}

}
