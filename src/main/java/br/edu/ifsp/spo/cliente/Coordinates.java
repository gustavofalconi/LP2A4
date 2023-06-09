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
public class Coordinates {
	@Column(name = "location_coordinates_latitude")
	private double latitude;
	@Column(name = "location_coordinates_longitude")
	private double longitude;
}
