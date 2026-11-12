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
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "marine_fleets")
public class MarineFleet {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@Column(name = "fleet_name", nullable = false, length = 250)
	@NotNull(message = "Fleet Name Cannot Be Null")
	@NotBlank(message = "Fleet Name Cannot Be Blank")
	@Size(max = 250, message = "Fleet name CANNOT be over 250 Characters!")
	private String fleetName;
	
	@Column(name = "admiral_name", nullable = false, length = 250)
	@NotNull(message = "Admiral Name Cannot BE Null")
	@NotBlank(message = "Admiral Name Cannot Be Blank")
	@Size(max = 250, message = "Admiral Name Cannot be Over 250 Characters")
	private String admiralName;
	
	
	
	
	@Column(name = "ship_count")
	@Min(value = 10, message = "Ship Count Cannot be Lower than 10")
	private Long shipCount = 10L;
	
	@Column(name = "assigned_sea", nullable = false, length = 250)
	@NotNull(message = "Assigned Sea Cannot Be Null")
	@NotBlank(message = "Assigned Sea Cannot Be Blank")
	@Size(max = 250, message = "Assigned Sea Cannot Be Over 250 Characters")
	private String assignedSea;
	
	
	@Column(name = "status")
	@Setter(AccessLevel.NONE)
	private String status = "STANDBY";
	
	public void dispatched() {
		this.status = "DISPATCHED";
	}
	public void busterCallActive() {
		this.status = "BUSTER_CALL_ACTIVE";
	}
	public void standBY() {
		this.status = "STANDBY";
	}

}
