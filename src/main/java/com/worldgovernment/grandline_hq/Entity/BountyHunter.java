package com.worldgovernment.grandline_hq.Entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "bounty_hunters")
public class BountyHunter {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	
	@Column(name = "name", nullable = false, length = 255)
	@Size(max = 255, message = "Bounty Hunter Name Cannot be Over 255 characters!")
	@NotNull(message = "Bounty Hunter name cannot Be over 255 characters")
	@NotBlank(message = "Bounty Hunter Name Cannot Be Blank")
	private String name;
	
	
	@Column(name = "bounty_claimed")
	@Setter(AccessLevel.NONE)
	private Integer bountyClaimed = 0;
	
	@Column(name = "total_bounty_cash")
	@Setter(AccessLevel.NONE)
	private Long total_bounty_cash = 0L;
	
	
	public void addBountyCash(Long cash) {
		if (cash > 0) {
			this.bountyClaimed += 1;
			this.total_bounty_cash += cash;
		}
		return;
	}

}
