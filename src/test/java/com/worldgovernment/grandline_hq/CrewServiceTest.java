package com.worldgovernment.grandline_hq;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.worldgovernment.grandline_hq.Entity.pirates;
import com.worldgovernment.grandline_hq.Repository.CrewRepository;
import com.worldgovernment.grandline_hq.Service.CrewService;
import com.worldgovernment.grandline_hq.Service.pirateService;

@ExtendWith(MockitoExtension.class)
public class CrewServiceTest {
	
	@Mock
	private pirateService pirateService;
	
	@Mock
	private CrewRepository crewRepo;
	
	@InjectMocks
	private CrewService crewService;
	
	
	@Test
	public void testproccesscreatecrew_Success() {
		UUID crewID = UUID.randomUUID();
		UUID pirateID = UUID.randomUUID();
		
		pirates pirate = new pirates();
		pirate.setId(pirateID);
		pirate.setName("Luffy");
	}

}
