package com.worldgovernment.grandline_hq.Entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class pirates {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@Column(name = "name", nullable = false, length = 255)
	@NotBlank(message = "Pirate Name Cannot Be Blank")
	@Size(max = 255, message = "Pirate name cannot be over 255 Characters!")
	@NotNull(message = "Pirate name Cannot be Null")
	private String name;
	
	@Column(name = "current_bounty", nullable = false)
	@NotNull(message = "Current bounty Cannot Be null")
	@Min(value = 0, message = "Current Bounty cannot Be Lower than 0")
	private Long currentBounty;
	
	@Column(name = "status", nullable = false, length = 255)
	@NotNull(message = "Status Cannot be Null")
	@NotBlank(message = "Status Cannot Be Blank")
	@Size(max = 255, message = "Status Cannot be more than 255 Characters!")
	private String status;
	
	@Version
	@Column(name = "version")
	private Long version = 0L;
	
	
	@ManyToOne
	@JoinColumn(name = "crew_id")
	private Crew crew;
	
	@OneToOne
	@JoinColumn(name = "devil_fruit_id")
	private DevilFruit devilfruit;

}
