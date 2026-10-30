package com.worldgovernment.grandline_hq.Entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "crews")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Crew {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@Column(name = "name", length = 255, nullable = false)
	@Size(max = 255, message = "Crew Name cannot be Longer than 255 characters!")
	@NotBlank(message = "Crew name cannot be blank!")
	@NotNull(message = "Crew Name Cannot be Null")
	private String name;
	
	@Column(name = "captain_id")
	private UUID captainId;
	
	@Column(name = "total_bounty", nullable = false)
	@NotNull(message = "total bounty cannot be null")
	@Min(value = 0, message =  "Total bounty cannot be negative!")
	private Long totalBounty = 0L;

}
