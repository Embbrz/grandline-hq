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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "devil_fruits")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DevilFruit {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@Column(name = "name", length = 255, nullable = false)
	@Size(max = 255, message = "Name is too long!")
	@NotBlank(message = "Name cannot be Blank!")
	@NotNull(message = "Name Cannot be Null")
	private String name;
	
	@Column(name = "fruit_type", length = 255, nullable = false)
	@Size(max = 255, message = "Fruit_Type Cannot be longer than 50 characters!")
	@NotBlank(message = "Fruit_Type Cannot Be Blank!")
	@NotNull(message = "Fruit Type Cannot Be Null")
	private String fruitType;
	
	@Column(name = "is_awakened", nullable = false)
	private Boolean isAwakened = false;

}
