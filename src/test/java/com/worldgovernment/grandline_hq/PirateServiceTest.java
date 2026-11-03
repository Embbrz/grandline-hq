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

import com.worldgovernment.grandline_hq.Entity.DevilFruit;
import com.worldgovernment.grandline_hq.Entity.pirates;
import com.worldgovernment.grandline_hq.Repository.FruitRepository;
import com.worldgovernment.grandline_hq.Repository.piraterepository;
import com.worldgovernment.grandline_hq.Service.FruitService;
import com.worldgovernment.grandline_hq.Service.pirateService;

@ExtendWith(MockitoExtension.class)
public class PirateServiceTest {
	@Mock
	private piraterepository piraterepo;
	
	@Mock
	private FruitRepository fruitRepo;
	
	@Mock
	private FruitService fruitService;
	
	@InjectMocks
	private pirateService pirateService;
	

	
	@Test
	void testproccesspirateEatFruit_Success() {
		UUID pirateID = UUID.randomUUID();
		UUID fruitID = UUID.randomUUID();
		
		pirates mockPirate = new pirates();
		mockPirate.setId(pirateID);
		mockPirate.setName("Luffy");
		
		DevilFruit mockFruit = new DevilFruit();
		mockFruit.setId(fruitID);
		mockFruit.setName("Gomu Gomu");
		mockFruit.setFruitType("Rubber Like abilities");
		
		when(fruitService.getById(any())).thenReturn(mockFruit);
		when(piraterepo.findById(any())).thenReturn(Optional.of(mockPirate));
		
		
		
		when(piraterepo.save(any())).thenReturn(mockPirate);
		
		pirateService.pirateEatFruit(pirateID, fruitID);
		
		assertEquals(mockPirate.getDevilfruit(), mockFruit);
		assertEquals(mockFruit.getIsConsumed(), true);
		
	}
	
	
	@Test
	void testproccesspirateEatFruit_PirateNotFound() {
		
		UUID fruitID = UUID.randomUUID();
		
		
		DevilFruit mockFruit = new DevilFruit();
		mockFruit.setId(fruitID);
		mockFruit.setName("Gomu Gomu");
		mockFruit.setFruitType("Rubber Like abilities");
		when(piraterepo.findById(any())).thenReturn(Optional.empty());
		org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
			pirateService.pirateEatFruit(UUID.randomUUID(), fruitID);
		});
	}
	
	@Test
	void testproccesspirateEatFruit_FruitNotFound() {
		UUID pirateID = UUID.randomUUID();
		
		
		pirates mockPirate = new pirates();
		mockPirate.setId(pirateID);
		mockPirate.setName("Luffy");
		
		org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
			pirateService.pirateEatFruit(pirateID, UUID.randomUUID());
		});
	}
	
	@Test
	void testproccesspirateEatFruit_PirateAlreadyEatenFruit() {
		UUID pirateID = UUID.randomUUID();
		UUID fruitID = UUID.randomUUID();
		
		pirates mockPirate = new pirates();
		mockPirate.setId(pirateID);
		mockPirate.setName("Luffy");
		
		DevilFruit mockFruit = new DevilFruit();
		mockFruit.setId(fruitID);
		mockFruit.setName("Gomu Gomu");
		mockFruit.setFruitType("Rubber Like abilities");
		
		UUID fruit2ID = UUID.randomUUID();
		DevilFruit mockFruit2 = new DevilFruit();
		
		mockFruit2.setId(fruit2ID);
		mockFruit2.setName("Nika Nika Fruitz");
		mockFruit2.setFruitType("Ability of imagination");
		
		when(piraterepo.findById(any())).thenReturn(Optional.of(mockPirate));
		when(fruitService.getById(any())).thenReturn(mockFruit).thenReturn(mockFruit2);
		when(piraterepo.save(any())).thenReturn(mockPirate);
		
		pirateService.pirateEatFruit(pirateID, fruitID);
		
		org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
			pirateService.pirateEatFruit(pirateID, fruit2ID);
		});
		
	}
	
	@Test
	void testproccesspirateEatFruit_FruitAlreadyConsumed() {
		UUID pirateID = UUID.randomUUID();
		UUID fruitID = UUID.randomUUID();
		
		pirates mockPirate = new pirates();
		mockPirate.setId(pirateID);
		mockPirate.setName("Luffy");
		
		DevilFruit mockFruit = new DevilFruit();
		mockFruit.setId(fruitID);
		mockFruit.setName("Gomu Gomu");
		mockFruit.setFruitType("Rubber Like abilities");
		mockFruit.setIsConsumed(true);
		
		when(piraterepo.findById(any())).thenReturn(Optional.of(mockPirate));
		when(fruitService.getById(any())).thenReturn(mockFruit);
		
		org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
			pirateService.pirateEatFruit(pirateID, fruitID);
		});
		
	}
	
	
	@Test
	void testproccessAddPirate_DevilFruitInUse() {
		UUID id = UUID.randomUUID();
		
		pirates mockPirate = new pirates();
		
		mockPirate.setId(id);
		mockPirate.setName("Luffy");
		
		DevilFruit fruit = new DevilFruit();
	    fruit.setIsConsumed(true);
	    
	    mockPirate.setDevilfruit(fruit);
	
	
		
		org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
			pirateService.addPirate(mockPirate);
		});
		
		
	}
	
	@Test
	void testproccesUpdateBounty_Success() {
		UUID id = UUID.randomUUID();
		
		pirates mockPirate = new pirates();
		mockPirate.setId(id);
		mockPirate.setName("Luffy");
		mockPirate.setCurrentBounty(100L);
		when(piraterepo.findById(any())).thenReturn(Optional.of(mockPirate));
		when(piraterepo.save(any())).thenReturn(mockPirate);
		
		pirateService.updateBounty(id, 100L);
		
		assertEquals(mockPirate.getCurrentBounty(), 200L);
		
	}
	
	
	@Test
	void testprocessUpdateBounty_PirateNotFound() {
		
		UUID id = UUID.randomUUID();
		
		when(piraterepo.findById(any())).thenReturn(Optional.empty());
		
		org.junit.jupiter.api.Assertions.assertThrows(RuntimeException.class, () -> {
			pirateService.updateBounty(id, 100L);
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
