package com.worldgovernment.grandline_hq.Entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bounty_claims")
public class BountyClaim {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@ManyToOne
	@JoinColumn(name = "pirate_id", nullable = false)
	@NotNull(message = "Pirate cannot be Null for a bounty claim!")
	private pirates pirate;
	
	@ManyToOne
	@JoinColumn(name = "hunter_id")
	@NotNull(message = "bounty Hunter cannot Be null for a Bounty Claim!")
	private BountyHunter bountyHunter;
	
	
	@Column(name = "reward_paid", nullable = false)
	@NotNull(message = "Reward paid cannot be null")
	@Min(value = 0, message = "Reward Paid cannot be Lower than 0")
	@Setter(AccessLevel.NONE)
	private Long rewardPaid;
	
	
	@Column(name = "pirate_is_alive")
	private Boolean pirateIsAlive = true;
	
	public void calculateReward() {
		if (this.pirateIsAlive) {
			this.rewardPaid = this.pirate.getCurrentBounty();
		}
		else {
			this.rewardPaid = (long) (this.pirate.getCurrentBounty() * 0.70);
		}
		return;
	}
	


}
