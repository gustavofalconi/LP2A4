package br.edu.ifsp.spo.cliente;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Location {
	
	@Column(name = "location_region")
	private String region;
	@Column(name = "location_street")
	private String street;
	@Column(name = "location_city")
	private String city;
	@Column(name = "location_state")
	private String state;
	@Column(name = "location_postcode")
	private int postcode;
	private Coordinates coordinates;
	private TimeZone timezone;
}
